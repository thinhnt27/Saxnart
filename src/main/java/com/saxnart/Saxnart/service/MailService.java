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
                helper.setSubject(email.getSubject());
                String htmlBody = "<html><head><meta charset=\"UTF-8\"></head><body><div style=\"text-align: center;\">"
                        + "<h2 style=\"font-weight: bold; color: black;\">Saxn'Art Club</h2>"
                        + "<div style=\"display: inline-block; background-color: #f0f0f0; padding: 20px; border-radius: 10px; text-align: left;\">"
                        + "<div style=\"border: 1px solid #000; border-radius: 10px; padding: 10px; color: black;\">"
                        + "<p>Xin chào ,</p>"
                        + "<p style=\"color: black;\">Saxn'Art xác nhận bạn đã đặt vé thành công lúc " + formatter.format(new Date())
                        + ". Bấm vào ... để xem chi tiết</p>"
                        + "<p style=\"font-weight: bold; color: black;\">Thông tin người nhận vé:</p>"
                        + "<table>"
                        + "<tr><td>Họ và tên:</td><td style=\"color: black;\"></td></tr>"
                        + "<tr><td>Số điện thoại:</td><td style=\"color: black;\"></td></tr>"
                        + "<tr><td>Email:</td><td style=\"color: black;\"></td></tr>"
                        + "</table>"
                        + "<p style=\"font-weight: bold; color: black;\">Chi tiết vé của bạn như sau:</p>"
                        + "<p style=\"color: black;\">Ghế đặt: </p>"
                        + "<p style=\"color: black;\">Thời gian: </p>"
                        + "<p style=\"color: black;\">Tổng tiền: </p>"
                        + "</div>"
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
