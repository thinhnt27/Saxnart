package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.dto.Email;
import com.saxnart.Saxnart.dto.request.UserPasswordUpdateDTO;
import com.saxnart.Saxnart.entity.UserEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.UserService;
import com.saxnart.Saxnart.utility.TokenChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
@PreAuthorize("hasRole('SUPERADMIN')")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getAllUser")
    public ResponseEntity<ResponseObject> getAllUser() {
        try {
            List<UserEntity> users = userService.getAllUser();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", users));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get ticket", ""));
        }
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<ResponseObject> getUser( @PathVariable Long userId) {
        try {
            UserEntity user = userService.getUserById(userId);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", user));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get ticket", ""));
        }
    }


    @PatchMapping("/changePassword/{userId}")
    public ResponseEntity<ResponseObject> changePassword(@RequestHeader("Authorization") String token,
                                                         @PathVariable Long userId,
                                                         @RequestBody UserPasswordUpdateDTO userPasswordUpdateDTO) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", "User ID cannot be null", ""));
        }
        try {
            if (TokenChecker.checkToken(token)) {
                userService.changePassword(userId, userPasswordUpdateDTO);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "Success", ""));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", "not found", ""));
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", ex.getMessage(), ""));
        }

    }

    @PatchMapping("/changePassword/{userId}/{otp}")
    public ResponseEntity<ResponseObject> changePassword(@PathVariable Long userId,
                                                         @PathVariable Long otp,
                                                         @RequestBody UserPasswordUpdateDTO userPasswordUpdateDTO) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", "User ID cannot be null", ""));
        }
        try {
            userService.changePasswordSuper(userId, otp, userPasswordUpdateDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "Success", ""));
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", ex.getMessage(), ""));
        }

    }

//    @PatchMapping("/changePasswordFix/{userId}")
//    public ResponseEntity<ResponseObject> changePassword(@PathVariable Long userId,
//                                                         @RequestBody UserPasswordUpdateDTO userPasswordUpdateDTO) {
//        if (userId == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ResponseObject("failed", "User ID cannot be null", ""));
//        }
//        try {
//            userService.changePasswordSuperFix(userId, userPasswordUpdateDTO);
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(new ResponseObject("ok", "Success", ""));
//        } catch (RuntimeException ex) {
//            System.out.println(ex.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ResponseObject("failed", ex.getMessage(), ""));
//        }
//
//    }

    @GetMapping("/validMail")
    public ResponseEntity<ResponseObject> validMail(@RequestHeader("Authorization") String token,
                                                    @RequestBody Email mail) {
        try {
            if (TokenChecker.checkToken(token)) {
                String message = userService.validMail(mail.getToEmail());
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "Success", message));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", "not found", ""));
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", ex.getMessage(), ""));
        }
    }


    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ResponseObject> deleteUser(@RequestHeader("Authorization") String token,
                                                     @PathVariable Long userId) {
        try {
            if (TokenChecker.checkToken(token)) {
                userService.delete(userId);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "Success", ""));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", "not found", ""));
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", ex.getMessage(), ""));
        }
    }

}
