package juust;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import juust.model.EmailInfo;
import juust.model.PlatterOrder;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import juust.service.PdfService;


public class PdfServiceTest {

    @Test
    public void test() throws Exception{
        ArrayList<PlatterOrder> list = new ArrayList<>();
        list.add(new PlatterOrder(1, "Väike", 65.00));
        list.add(new PlatterOrder(1, "Suur", 85.00));
        EmailInfo request = new EmailInfo();
        request.setPlatterOrders(list);
        request.setName("Jürgen");
        request.setPrice(65.00);
        request.setEmail("hampsu17@gmail.com");
        request.setId(50L);
        request.setDate(new Date());
        FileUtils.copyFile(new PdfService().createPdf(request), new File("iTextTable.pdf"));
    }
}
