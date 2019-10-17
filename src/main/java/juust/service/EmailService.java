package juust.service;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import juust.model.EmailInfo;
import juust.model.PlatterOrder;
import juust.request.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class EmailService {

    @Value( "${email.user}" )
    private String user;

    @Value( "${email.pass}" )
    private String pass;

//    @Value("classpath:/email/basic_email.html")
//    Resource resourceFile;

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

            // For testing
//            String msg = asString(resourceFile).replace("clientName", emailInfo.getName());
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

    public void sendOrderEmail(EmailInfo emailInfo) {
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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("juustuunelm@gmail.com"));
            message.setSubject("Tellimus " + emailInfo.getName());

            String msg = "Tellija: " + emailInfo.getName() + " aeg: " + emailInfo.getDate();
            for (PlatterOrder po : emailInfo.getPlatterOrders()) {
                msg += " vaagen: " + po.getName() + " kogus: " + po.getNumber();
            }

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
            message.setFrom(new InternetAddress(request.getEmailAddress(), encode(request.getName())));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("juustuunelm@gmail.com"));
            message.setSubject(encode(request.getName() + " küsimus"));

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

    private String encode(String s) throws UnsupportedEncodingException {
        return MimeUtility.encodeText(s, "utf-8", "B");
    }

//    private String asString(Resource resource) {
//        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
//            return FileCopyUtils.copyToString(reader);
//        } catch (IOException e) {
//            throw new UncheckedIOException(e);
//        }
//    }
}
