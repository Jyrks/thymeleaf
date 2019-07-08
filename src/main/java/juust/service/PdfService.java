package juust.service;

import static com.itextpdf.text.Chunk.NEWLINE;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import juust.model.EmailInfo;
import juust.model.PlatterOrder;
import org.springframework.stereotype.Service;

import com.google.common.io.Files;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfService {

    public File createPdf(EmailInfo emailInfo) throws Exception{
        Document document = new Document();
        File file = new File(Files.createTempDir(), "Arve" + emailInfo.getId() + ".pdf");
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        document.add(new Paragraph("HENOK OÜ", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
        document.add(createEmptyRows(3));

        Image img = Image.getInstance(ClassLoader.getSystemResource("static/img/core-img/logo.png"));
        img.setAbsolutePosition(430f, 740f);
        document.add(img);

        PdfPTable billInfo = new PdfPTable(8);
        billInfo.setWidthPercentage(100);
        billInfo.addCell(createBoldCell("Arve nr:"));
        billInfo.addCell(createCell("" + emailInfo.getId()));
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
        billInfo.addCell(createCell(emailInfo.getName()));
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

        createOrders(emailInfo.getPlatterOrders(), table);

        addEmptyCells(3, table);
        addEmptyCells(3, table);

        addEmptyCells(1, table);
        table.addCell(createCell("Käibemaks:"));
        table.addCell(createCell("0"));
        addEmptyCells(1, table);
        table.addCell(createBoldCell("KOKKU (EUR):"));
        table.addCell(createBoldCell("" + emailInfo.getPrice()));
        document.add(table);
        document.add(createEmptyRows(25 -  emailInfo.getPlatterOrders().size()));

        drawLine(36, 559, 92, writer);

        PdfPTable footer = new PdfPTable(3);
        footer.setWidthPercentage(100);
        footer.addCell(createCell("HENOK OÜ"));
        footer.addCell(createCell("Telefon: 53826845"));
        footer.addCell(createCell("LHV"));
        footer.addCell(createCell("Puhke 3-1"));
        footer.addCell(createCell("Email: juustuunelm@gmail.com"));
        footer.addCell(createCell("EE477700771003390826"));
        footer.addCell(createCell("Tallinn 10135"));
        footer.addCell(createCell("Reg nr. 14604836"));
        addEmptyCells(1, footer);

        document.add(footer);

        document.close();

        return file;
    }

    private void createOrders(List<PlatterOrder> platterOrders, PdfPTable table) {
        for (PlatterOrder po : platterOrders) {
            table.addCell(createCell(po.getName()));
            table.addCell(createCell("     " + po.getNumber()));
            table.addCell(createCell("" + po.getPrice()));
        }
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

    private void addEmptyCells(int emptyCells, PdfPTable table) {
        for (int i = 0; i < emptyCells; i++) {
            table.addCell(createCell(" "));
        }
    }
}
