package juust.service;

import juust.dao.OrdersDao;
import juust.model.Order;
import juust.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static juust.util.DateUtil.createUserDate;

@Service
public class OrderService {

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private EmailService emailService;

    public void createOrder(OrderRequest request) {


        Order o = new Order();
        o.setPrice(79.99);
        o.setOrderName(request.getPlatterName());
        o.setPersonName(request.getPersonName());
        o.setEmail(request.getEmail());
        o.setPhone(request.getPhoneNumber());
        o.setDate(createUserDate(request.getDate(), request.getTime()));
        o.setCreated(new Date());

//        OrderRequest(name=Jürgen, email=hampsu17@gmail.com, phoneNumber=58120546, time=19:00, date=17/05/2019)

        ordersDao.insertOrder(o);

//        emailService.sendEmail();

        System.out.println(request);
    }
}
