package sample.extraClasses;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class TestingEmail {
    public static void main(String[] args) throws MessagingException {
        sendingEmail("dshahadat3@gmail.com, dshahadat10@gmail.com");
    }
    private static void sendingEmail(String emails) throws MessagingException {
        String gUsername = "foodo5999@gmail.com";
        String password = "AOOP159753";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(gUsername, password);
            }
        });
        session.setDebug(true);

        MimeMessage message = new MimeMessage(session);
        message.setFrom("pmostafa1067@gmail.com");

        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(emails));

        message.setSubject("Hey checkout the new item in our list");
        message.setText("Hi\nThis is a test mailing system\nHope you like it");
        Transport.send(message);
        System.out.println("Mail Sent");
    }
}
