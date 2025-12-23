package com.example.Finanzas.Inteligentes.service.impl;

import com.example.Finanzas.Inteligentes.service.MailService;
import com.example.Finanzas.Inteligentes.utils.MailTemplate;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailServImpl implements MailService {
    @Override
    public void sendMail(String to, String subject, String body) {
             final String username = "finanzasinteligentes@arccanto.com";
       final String password = "srgjrbstfzzfkpqz"; 
       Properties prop =new Properties();
        prop.put("mail.smtp.host", "smtp-mail.outlook.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        Session session =Session.getInstance(
                prop,new javax.mail.Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,password);
                    }
                });
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("finanzasinteligentes@arccanto.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            //Asigna contenido adaptando lo solicitado
            message.setContent(MailTemplate.MAIL_FI.replace("{{message}}", body),"text/html; charset=utf-8");
            Transport.send(message);
        }catch(MessagingException e){
            e.printStackTrace();
        }
    }
}
