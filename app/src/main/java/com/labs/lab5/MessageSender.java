package com.labs.lab5;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

class MessageSender {
    //private static final String SMTP_SERVER    = "smtp.mail.ru";
    private static final String SMTP_SERVER    = "smtp.gmail.ru";
    //private static final String SMTP_PORT      = "465";
    private static final String SMTP_PORT      = "587";
    private static final String SMTP_AUTH_USER = "kurako.kirill@gmail.com";
    private static final String SMTP_AUTH_PWD  = "KRaN3922614";
    private static final String EMAIL_FROM     = "kurako.kirill@gmail.com";

    private Message message = null;

    void sendMessage (final String text)
    {
        try {
            Multipart mmp = new MimeMultipart();
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(text, "text/plain; charset=utf-8");
            mmp.addBodyPart(bodyPart);
            message.setContent(mmp);
            Transport.send(message);
        } catch (MessagingException e){
            e.printStackTrace();
        }
    }


    MessageSender(final String emailTo, final String topic)
    {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_SERVER);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        try {
            Authenticator auth = new EmailAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
            Session session = Session.getInstance(properties, auth);
            session.setDebug(true);

            InternetAddress emailFromAddress = new InternetAddress(EMAIL_FROM);
            InternetAddress emailToAddress = new InternetAddress(emailTo);
            message = new MimeMessage(session);
            message.setFrom(emailFromAddress);
            message.setRecipient(Message.RecipientType.TO, emailToAddress);
            message.setSubject(topic);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
