package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.entity.ContactEntity;
import com.saxnart.Saxnart.entity.FeedbackEntity;
import com.saxnart.Saxnart.repository.ContactRepository;
import com.saxnart.Saxnart.repository.FeedbackRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public List<ContactEntity> getAllContacts(){
        return contactRepository.findAll();
    }

    public ContactEntity getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found with id: " + id));
    }

    public ContactEntity saveContact(ContactEntity feedback) {
        return contactRepository.save(feedback);
    }

    public ContactEntity updateContact(Long feedbackId){
        Optional<ContactEntity> contactOptional = contactRepository.findById(feedbackId);
        if (contactOptional.isPresent()) {
            ContactEntity contact = contactOptional.get();
            contact.setStatus(true);
            contactRepository.save(contact);
            return contact;
        }else return contactOptional.get();
    }
}
