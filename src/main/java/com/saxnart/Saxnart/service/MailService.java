package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.entity.MailEntity;
import com.saxnart.Saxnart.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MailService {
    @Autowired
    private MailRepository mailRepository;

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
