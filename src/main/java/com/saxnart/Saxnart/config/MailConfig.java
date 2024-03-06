package com.saxnart.Saxnart.config;

import com.saxnart.Saxnart.entity.MailConfigEntity;
import com.saxnart.Saxnart.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Autowired
    private MailRepository mailRepository;
    @Bean
    public JavaMailSender javaMailSender() throws Exception {
        MailConfigEntity mailConfigEntity = mailRepository.findAll().stream().findFirst().orElse(null);
        if (mailConfigEntity != null){
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(mailConfigEntity.getHost());
            mailSender.setPort(mailConfigEntity.getPort());
            mailSender.setUsername(mailConfigEntity.getUsername());
            mailSender.setPassword(mailConfigEntity.getPassword());
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.smtp.auth", mailConfigEntity.getSmtpAuth());
            props.put("mail.smtp.starttls.enable", mailConfigEntity.getSmtpStartTlsEnable());
            return mailSender;
        }
        throw new Exception("Lỗi rồi");
    }
}
