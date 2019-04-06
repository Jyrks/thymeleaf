package juust;

import static com.itextpdf.text.Chunk.NEWLINE;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
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

        document.add(new Paragraph("Juustuunelm OÜ"));
        document.add(createEmptyRows(3));

        document.add(new Paragraph("Arve nr: " + 1));
        document.add(new Paragraph("Kuupäev: " + createDate(0)));
        document.add(new Paragraph("Tähtaeg: " + createDate(7)));
        document.add(createEmptyRows(2));

        document.add(new Paragraph("Maksja: " + "kliendiNimi"));
        document.add(createEmptyRows(2));

        float[] columnWidths = {4, 2, 2};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.addCell(createCellWithoutBorders("Toode"));
        table.addCell(createCellWithoutBorders("Kogus"));
        table.addCell(createCellWithoutBorders("Hind"));
        createEmptyRows(3, 1, table);
        drawLine(36, 559, 580, writer);

        table.addCell(createCellWithoutBorders("Mahe vaagen"));
        table.addCell(createCellWithoutBorders("1"));
        table.addCell(createCellWithoutBorders("80.00"));
        document.add(table);
        document.add(createEmptyRows(2));

        document.add(alignRight("KOKKU: " + "80.00"));
        document.add(createEmptyRows(10));

        document.add(new Paragraph("Heveli Enok"));
        document.add(new Paragraph("Kandikumeister"));
        document.add(createEmptyRows(14));
        drawLine(36, 559, 85, writer);

        PdfPTable footer = new PdfPTable(3);
        footer.setWidthPercentage(100);
        footer.addCell(createCellWithoutBorders("Juustuunelm OÜ"));
        footer.addCell(createCellWithoutBorders("Telefon: 53535353"));
        footer.addCell(createCellWithoutBorders("LHV"));
        footer.addCell(createCellWithoutBorders("Lasnamjäe 1"));
        footer.addCell(createCellWithoutBorders("Email: hevelienok@gmail.com"));
        footer.addCell(createCellWithoutBorders("IBAN123323232323232"));
        footer.addCell(createCellWithoutBorders("Tallinn 13434"));
        footer.addCell(createCellWithoutBorders("Reg nr. 1432424"));
        footer.addCell(createCellWithoutBorders(" "));

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

    private PdfPCell createCellWithoutBorders(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private void createEmptyRows(int columns, int emptyLines, PdfPTable table) {
        for (int i = 0; i < columns * emptyLines; i++) {
            table.addCell(createCellWithoutBorders(" "));
        }
    }

    private Paragraph alignRight(String text) {
        Paragraph p = new Paragraph(text);
        p.setAlignment(Element.ALIGN_RIGHT);
        return p;
    }
}
