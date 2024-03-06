package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.Email;
import com.saxnart.Saxnart.entity.MailConfigEntity;
import com.saxnart.Saxnart.repository.MailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class MailService {
    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private JavaMailSender javaMailSender;


    public MailConfigEntity getMailConfig() {
        return mailRepository.findAll().stream().findFirst().orElse(null);
    }
    public void sendMail(Email email) {
        MailConfigEntity mailConfigEntity = mailRepository.findAll().stream().findFirst().orElse(null);
        if (mailConfigEntity != null) {
            MimeMessage message = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(mailConfigEntity.getUsername());
                helper.setTo(email.getToEmail());
                helper.setSubject(email.getSubject());
                String htmlBody = "<html><body><div style=\"text-align: center;\">"
                        + "<div style=\"display: inline-block; background-color: #f0f0f0; padding: 20px; border-radius: 10px;\">"
                        + "<img src=\"https://maybanhang.net/wp-content/uploads/2016/01/dau-tich-mau-xanh.png\" style=\"width: 50px; height: 50px;\">"
                        + "<p style=\"color: green; margin-top: 10px;\">Thành công</p>"
                        + "</div></div></body></html>";
                helper.setText(htmlBody, true);
                javaMailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
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
            return mailRepository.save(existingMail);
        }
        return null;
    }
}
