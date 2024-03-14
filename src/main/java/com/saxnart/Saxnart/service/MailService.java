package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.Email;
import com.saxnart.Saxnart.entity.MailConfigEntity;
import com.saxnart.Saxnart.repository.MailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class MailService {
    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(Email email) {
        MailConfigEntity mailConfigEntity = mailRepository.findAll().stream().findFirst().orElse(null);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        if (mailConfigEntity != null) {
            MimeMessage message = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, false,"UTF-8");
                helper.setFrom(mailConfigEntity.getUsername());
                helper.setTo(email.getToEmail());
                message.setSubject("[Saxn'Art] - Test");
                message.setText("Test");
                javaMailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
    public String validMail(String mail) {
        sendEmail(mail, "[Sax'nArt]-Test", "Test");
        return "Success";
    }
    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public List<MailConfigEntity> getAllMails() {
        return mailRepository.findAll();
    }
    public MailConfigEntity updateMail(Long id, MailConfigEntity mailConfigEntity) {
        Optional<MailConfigEntity> optionalMailEntity = mailRepository.findById(id);
        if (optionalMailEntity.isPresent()) {
            MailConfigEntity existingMail = optionalMailEntity.get();
            existingMail.setHost(mailConfigEntity.getHost());
            existingMail.setPort(mailConfigEntity.getPort());
            existingMail.setUsername(mailConfigEntity.getUsername());
            existingMail.setPassword(mailConfigEntity.getPassword());
            existingMail.setSmtpAuth(mailConfigEntity.getSmtpAuth());
            existingMail.setSmtpStartTlsEnable(mailConfigEntity.getSmtpStartTlsEnable());
            existingMail.setDateModified(new Date());
            existingMail.setAuthorModified(mailConfigEntity.getAuthorModified());
            return mailRepository.save(existingMail);
        }
        return null;
    }
}
