/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailer;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author mobyr
 */
public class SendMails {
    private final String from = "eposta", pass = "sifre";
    private final String host = "smtp.gmail.com";
    Properties properties = new Properties();
    Session session;
    
    public SendMails(String to, String book_name, String old_price, String new_price, String username, String url)
    {
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        session = Session.getInstance(properties, new javax.mail.Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("eposta", "sifre");

            }

        });
        
        session.setDebug(true);
        
        try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // Set Subject: header field
         message.setSubject("Bir Kitap İndirime Girdi", "UTF-8");

         // Now set the actual message
         message.setContent(
                 String.format("<h3>Merhaba %s,</h3><br>"
                         + "Favorilerinize eklemiş olduğunuz %s adlı kitap indirime girmiştir.<br>"
                         + "<br><b><del>Eski Fiyat: %s₺</del></b>"
                         + "<br><b>Yeni Fiyat: %s₺</b>"
                         + "<br>URL: %s", username, book_name, old_price, new_price, url), "text/html; charset=utf-8");

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
            System.out.println(mex);
      }
    }
}
