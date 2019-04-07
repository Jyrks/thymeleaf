package juust;

import org.junit.Test;

import juust.service.PdfService;


public class PdfServiceTest {

    @Test
    public void test() throws Exception{
        new PdfService().createPdf();
    }
}
