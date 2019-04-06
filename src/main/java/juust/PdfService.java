package juust;

import static com.itextpdf.text.Chunk.NEWLINE;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfService {

    public void createPdf() throws Exception{
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("iTextTable.pdf"));
        document.open();

        document.add(new Paragraph("Juustuunelm OÜ", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
        document.add(createEmptyRows(3));

        PdfPTable billInfo = new PdfPTable(8);
        billInfo.setWidthPercentage(100);
        billInfo.addCell(createBoldCell("Arve nr:"));
        billInfo.addCell(createCell("1"));
        addEmptyCells(6, billInfo);
        billInfo.addCell(createBoldCell("Kuupäev:"));
        billInfo.addCell(createCell(createDate(0)));
        addEmptyCells(6, billInfo);
        billInfo.addCell(createBoldCell("Tähtaeg:"));
        billInfo.addCell(createCell(createDate(7)));
        addEmptyCells(6, billInfo);
        addEmptyCells(8, billInfo);
        addEmptyCells(8, billInfo);

        billInfo.addCell(createBoldCell("Maksja:"));
        billInfo.addCell(createCell("kliendiNimi"));
        addEmptyCells(6, billInfo);
        document.add(billInfo);
        document.add(createEmptyRows(3));

        float[] columnWidths = {4, 2, 2};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.addCell(createBoldCell("Toode"));
        table.addCell(createBoldCell("Kogus"));
        table.addCell(createBoldCell("Hind"));
        addEmptyCells(3, table);
        drawLine(36, 559, 565, writer);

        table.addCell(createCell("Mahe vaagen"));
        table.addCell(createCell("1"));
        table.addCell(createCell("80.00"));
        document.add(table);
        document.add(createEmptyRows(2));

        document.add(alignRightBold("KOKKU: " + "80.00"));
        document.add(createEmptyRows(10));

        document.add(new Paragraph("Heveli Enok"));
        document.add(new Paragraph("Kandikumeister"));
        document.add(createEmptyRows(13));
        drawLine(36, 559, 90, writer);

        PdfPTable footer = new PdfPTable(3);
        footer.setWidthPercentage(100);
        footer.addCell(createCell("Juustuunelm OÜ"));
        footer.addCell(createCell("Telefon: 53535353"));
        footer.addCell(createCell("LHV"));
        footer.addCell(createCell("Lasnamjäe 1"));
        footer.addCell(createCell("Email: hevelienok@gmail.com"));
        footer.addCell(createCell("IBAN123323232323232"));
        footer.addCell(createCell("Tallinn 13434"));
        footer.addCell(createCell("Reg nr. 1432424"));
        footer.addCell(createCell(" "));

        document.add(footer);

        document.close();
    }

    private Paragraph createEmptyRows(int times) {
        Paragraph p = new Paragraph();
        for (int i = 0; i < times; i++) {
            p.add(NEWLINE);
        }
        return p;
    }

    private String createDate(long plusDays) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        date = date.plusDays(plusDays);

        return date.format(myFormatObj);
    }

    private void drawLine(int leftX, int rightX, int heightY, PdfWriter writer) {
        PdfContentByte canvas = writer.getDirectContent();
        canvas.moveTo(leftX, heightY);
        canvas.lineTo(rightX, heightY);
        canvas.closePathStroke();
    }

    private PdfPCell createCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private PdfPCell createBoldCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private Paragraph alignRightBold(String text) {
        Paragraph p = new Paragraph(text, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
        p.setAlignment(Element.ALIGN_RIGHT);
        return p;
    }

    private void addEmptyCells(int emptyCells, PdfPTable table) {
        for (int i = 0; i < emptyCells; i++) {
            table.addCell(createCell(" "));
        }
    }
}
