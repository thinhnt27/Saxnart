package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.dto.Email;
import com.saxnart.Saxnart.entity.MailEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mail")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllMails() {
        try {
            List<MailEntity> mailEntities = mailService.getAllMails();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", mailEntities));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error getting all mails", ""));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> updateMail(@PathVariable Long id, @RequestBody MailEntity mailEntity) {
        try {
            if(!id.equals(mailEntity.getId())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("error", "Id is not match", ""));
            }
            if (!mailEntity.getIsSecure().equals("true") && !mailEntity.getIsSecure().equals("false")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("error", "Just true or false", ""));
            }
            MailEntity updatedMail = mailService.updateMail(id, mailEntity);
            if (updatedMail != null) {
                return ResponseEntity.ok(new ResponseObject("ok", "Mail updated successfully", updatedMail));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("error", "Mail not found", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error updating mail", ""));
        }
    }

    @PostMapping()
    public ResponseEntity<?> sendMail(@Valid @RequestBody Email email){
        mailService.sendMail(email);
        return ResponseEntity.ok(new ResponseObject("ok", "Mail sent successfully", ""));
    }
}
