package juust.service;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import juust.model.EmailInfo;
import juust.request.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
public class EmailService {

    @Value( "${email.user}" )
    private String user;

    @Value( "${email.pass}" )
    private String pass;

    @Autowired
    private PdfService pdfService;

    public void sendEmail(EmailInfo emailInfo) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("juustuunelm@gmail.com", "Juustuunelm"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailInfo.getEmail()));
            message.setSubject("Juustuvaagna tellimus");

            String msg = Resources.toString(getClass().getClassLoader().getResource("/email/basic_email.html"), Charsets.UTF_8).replace("clientName", emailInfo.getName());

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(pdfService.createPdf(emailInfo));
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendUserEmail(EmailRequest request) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(request.getEmailAddress(), request.getName()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("juustuunelm@gmail.com"));
            message.setSubject(request.getName() + " küsimus");
            message.setReplyTo(new Address[]
                {
                    new javax.mail.internet.InternetAddress(request.getEmailAddress())
                });

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(request.getEmail(), "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
