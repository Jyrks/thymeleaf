package juust;

import juust.model.EmailInfo;
import juust.model.PlatterOrder;
import juust.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void test() {
        ArrayList<PlatterOrder> list = new ArrayList<>();
        list.add(new PlatterOrder(1, "Väike", 65.00));
//        list.add(new PlatterOrder(1, "Suur", 85.00));
        EmailInfo request = new EmailInfo();
        request.setPlatterOrders(list);
        request.setName("Jürgen");
        request.setPrice(65.00);
        request.setEmail("hampsu17@gmail.com");
        request.setId(50L);
        request.setDate(new Date());
        emailService.sendEmail(request);
    }
}
