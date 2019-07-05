package juust.service;

import juust.dao.OrdersDao;
import juust.dao.PlatterDao;
import juust.model.EmailInfo;
import juust.model.Order;
import juust.model.Platter;
import juust.model.PlatterOrder;
import juust.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static juust.util.DateUtil.createUserDate;

@Service
public class OrderService {

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private PlatterDao platterDao;

    @Autowired
    private EmailService emailService;

    public void createOrder(OrderRequest request) {

        double price = 0.0;
        List<PlatterOrder> list = new ArrayList<>();
        for (String s : request.getPlatters()) {
            Platter p = platterDao.getPlatterByName(s.split(" ")[0]);
            PlatterOrder po = new PlatterOrder();
            po.setName(p.getName());
            po.setPrice(p.getPrice());
            po.setNumber(Integer.valueOf(s.split(" ")[1]));
            price += p.getPrice() * (double) po.getNumber();
            list.add(po);
        }

        Order o = new Order();
        o.setPrice(price);
        o.setOrderName(request.getPlatters().toString());
        o.setPersonName(request.getPersonName());
        o.setEmail(request.getEmail());
        o.setPhone(request.getPhoneNumber());
        o.setDate(createUserDate(request.getDate(), request.getTime()));
        o.setCreated(new Date());

//        OrderRequest(name=Jürgen, email=hampsu17@gmail.com, phoneNumber=58120546, time=19:00, date=17/05/2019)

        ordersDao.insertOrder(o);

        EmailInfo e = new EmailInfo();
        e.setDate(o.getDate());
        e.setId((ordersDao.getOrderId()));
        e.setPlatterOrders(list);
        e.setEmail(o.getEmail());
        e.setPrice(o.getPrice());
        e.setName(o.getPersonName());

        emailService.sendEmail(e);

        System.out.println(request);
    }
}
