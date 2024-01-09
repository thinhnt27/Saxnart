package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.entity.ContactEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/contact")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
public class ContactController {

    @Autowired
    private ContactService contactService;
    @GetMapping
    public ResponseEntity<ResponseObject> getAllContacts() {
        try {
            List<ContactEntity> contacts = contactService.getAllContacts();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", contacts));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all contact", ""));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getContactById(@PathVariable Long id) {
        try {
            ContactEntity contact = contactService.getContactById(id);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", contact));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get a contact", ""));
        }
    }

    @PostMapping("/createContact")
    public ResponseEntity<ResponseObject> saveContact(@RequestBody ContactEntity contact) {
        try {
            ContactEntity savedContact = contactService.saveContact(contact);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", savedContact));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all contact", ""));
        }
    }

    @PatchMapping("isRead/{contactId}")
    public ResponseEntity<ResponseObject> markContactAsRead(@PathVariable Long contactId) {
        try {
            contactService.updateContact(contactId);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", contactService.updateContact(contactId)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all contact", ""));
        }
    }
}
