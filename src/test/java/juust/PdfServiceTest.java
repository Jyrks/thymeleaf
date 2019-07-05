package juust;

import java.io.File;

import juust.model.EmailInfo;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import juust.service.PdfService;


public class PdfServiceTest {

    @Test
    public void test() throws Exception{
        FileUtils.copyFile(new PdfService().createPdf(new EmailInfo()), new File("iTextTable.pdf"));
    }
}
