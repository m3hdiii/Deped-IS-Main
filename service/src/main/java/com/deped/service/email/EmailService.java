package com.deped.service.email;

import com.deped.config.SharedConfigData;
import com.deped.model.config.server.ServerEnumKey;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Map;
import java.util.Properties;

public class EmailService {

    public static boolean sendEmail(String sendToEmail, String subject, String text) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Map<ServerEnumKey, String> configData = SharedConfigData.getAppConfigs(false);

        mailSender.setHost(configData.get(ServerEnumKey.MAIL_HOST));
        mailSender.setPort(Integer.valueOf(configData.get(ServerEnumKey.MAIL_PORT_NUMBER)));
        mailSender.setUsername(configData.get(ServerEnumKey.MAIL_USERNAME));
        mailSender.setPassword(configData.get(ServerEnumKey.MAIL_PASSWORD));
        mailSender.setJavaMailProperties(props);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(configData.get(ServerEnumKey.MAIL_USERNAME));
        message.setTo(sendToEmail);
        message.setSubject(subject);
        message.setText(text);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        sendEmail("2108867@slu.edu.ph", "Testi", "Just for a test purposes");
    }
}
