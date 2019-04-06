package juust;

import org.junit.Test;


public class PdfServiceTest {

    @Test
    public void test() throws Exception{
        new PdfService().createPdf();
    }
}
