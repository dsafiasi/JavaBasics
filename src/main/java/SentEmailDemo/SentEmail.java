package com.jdbc.SentEmailDemo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SentEmail {
    // 服务器地址:
    private static final String smtp = "smtp.qq.com";
    // 登录用户名:
    private static final String username = "965297162@qq.com";
    // 登录口令:
    private static final String password = "btehgvxvmyhgbaja";

    public static void main(String[] args) {
        // 连接到SMTP服务器587端口:
        Properties props = new Properties();
        props.put("mail.smtp.host", smtp); // SMTP主机名
        props.put("mail.smtp.port", "587"); // 主机端口号
        props.put("mail.smtp.auth", "true"); // 是否需要用户认证
        props.put("mail.smtp.starttls.enable", "true"); // 启用TLS加密
        // 获取Session实例:
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        // 设置debug模式便于调试:
        session.setDebug(true);

        MimeMessage message = new MimeMessage(session);
        try {
            // 设置发送方地址:
            message.setFrom(new InternetAddress(username));
            // 设置接收方地址:
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("ad800911@126.com"));
            // 设置邮件主题:
            message.setSubject("Hello", "UTF-8");
            // 设置邮件正文:
            message.setText("你好", "UTF-8");
            // 发送:
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }








}
