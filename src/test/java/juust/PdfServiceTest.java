package juust;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import juust.service.PdfService;


public class PdfServiceTest {

    @Test
    public void test() throws Exception{
        FileUtils.copyFile(new PdfService().createPdf(), new File("iTextTable.pdf"));
    }
}
