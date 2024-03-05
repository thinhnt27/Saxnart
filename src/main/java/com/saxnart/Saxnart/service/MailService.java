package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.Email;
import com.saxnart.Saxnart.entity.MailEntity;
import com.saxnart.Saxnart.repository.MailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MailService {
    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(Email email){
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("thinhntse170224@fpt.edu.vn");
            helper.setTo(email.getToEmail());
            helper.setSubject(email.getSubject());
            String htmlBody = "<html><body><h1>Hello</h1><p style=\"color:red;\">This is a test email with HTML and CSS content.</p></body></html>";
            helper.setText(htmlBody, true);
            javaMailSender.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }

    }

    public List<MailEntity> getAllMails() {
        return mailRepository.findAll();
    }
    public MailEntity updateMail(Long id, MailEntity mailEntity) {
        Optional<MailEntity> optionalMailEntity = mailRepository.findById(id);
        if (optionalMailEntity.isPresent()) {
            MailEntity existingMail = optionalMailEntity.get();
            existingMail.setSMTPHost(mailEntity.getSMTPHost());
            existingMail.setSMTPPort(mailEntity.getSMTPPort());
            existingMail.setSMTPUsername(mailEntity.getSMTPUsername());
            existingMail.setSMTPPassword(mailEntity.getSMTPPassword());
            existingMail.setIsSecure(mailEntity.getIsSecure());
            return mailRepository.save(existingMail);
        }
        return null;
    }
}
