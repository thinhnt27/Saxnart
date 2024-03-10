package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.Email;
import com.saxnart.Saxnart.dto.request.UserPasswordUpdateDTO;
import com.saxnart.Saxnart.entity.MailConfigEntity;
import com.saxnart.Saxnart.entity.RoleEntity;
import com.saxnart.Saxnart.entity.UserEntity;
import com.saxnart.Saxnart.extention.UserException;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.repository.MailRepository;
import com.saxnart.Saxnart.repository.RoleRepository;
import com.saxnart.Saxnart.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private JavaMailSender javaMailSender;
//    private final BlogPostRepository blogPostRepository;
//    private final VoteRepository voteRepository;
//    private final CommentRepository commentRepository;

//    @Autowired
//    public UserService(UserRepository userRepository,
//                       BlogPostRepository blogPostRepository, VoteRepository voteRepository, CommentRepository commentRepository) {
//        this.userRepository = userRepository;
//        this.blogPostRepository = blogPostRepository;
//        this.voteRepository = voteRepository;
//        this.commentRepository = commentRepository;
//    }


    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private Hashing hashing;

    @Autowired
    PasswordEncoder encoder;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserEntity saveUser(UserEntity user) {
//        String pass = hashing.hasdPassword(user.getHashed_password());
//        user.setHashed_password(pass);
        user.setHashedpassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public RoleEntity saveRole(RoleEntity role) {
        return null;
    }


    public void addToUser(String username, String rolename) {
        UserEntity user = userRepository.findByUsernameAndStatusTrue(username).get();
        RoleEntity role = roleRepository.findByName(rolename);
        user.getRoles().add(role);
    }


//    public List<UserInfoResponseDTO> getActiveUser() {
//        List<UserEntity> userEntities = userRepository.findAllByOrderByPointDesc();
//        List<UserInfoResponseDTO> highestPointUser = new ArrayList<>();
//
//        for (UserEntity user : userEntities) {
//            if (user.getPoint().equals(userEntities.get(0).getPoint())) {
//                UserInfoResponseDTO userInfoResponseDTO =
//                        DTOConverter.convertUserDTO(user);
//
//                highestPointUser.add(userInfoResponseDTO);
//            }
//        }
//
//        return highestPointUser;
//    }
//
//
//    public UserInfoResponseDTO getUserInfo(Long userId) {
//        UserEntity user = userRepository.findByIdAndStatusIsTrue(userId);
//        if (user != null) {
//            return DTOConverter.convertUserDTO(user);
//        } else throw new UserException("");
//    }
//
//
    public List<UserEntity> getAllUser() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserEntity> userList = new ArrayList<>();
        for (UserEntity userEntity: userEntities) {
            userEntity.setHashedpassword(null);
            userList.add(userEntity);
        }
        return userList;
    }

    public UserEntity getUserById(Long userId) {

        return userRepository.findById(userId).orElse(null);
    }

    public ResponseEntity<ResponseObject> deleteUser(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (userEntity.isPresent()
                && userEntity.get().getStatus()) {
            UserEntity user = this.getUserById(userId);
            user.setStatus(false);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("OK", "deleted successfully", user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject("Not found", "user not found", ""));
    }

//    public ResponseEntity<ResponseObject> updateUser(Long userId, UserDTO userDTO) {
//
//        Optional<UserEntity> userEntity = userRepository.findById(userId);
//        if (userEntity.isPresent()) {
//            if (userDTO.getRole() != null) {
//                Set<RoleEntity> roleEntities = new HashSet<>();
//                RoleEntity userRole = roleRepository.findByName(userDTO.getRole().toUpperCase());
//                roleEntities.add(userRole);
//
//                UserEntity user = this.getUserById(userId);
//                user.setFullName(userDTO.getFullName());
//                user.setEmail(userDTO.getEmail());
//                user.setPicture(userDTO.getPicture());
//                user.setRoles(roleEntities);
//                user.setStatus(userDTO.getStatus());
//                userRepository.save(user);
//                return ResponseEntity.status(HttpStatus.OK)
//                        .body(new ResponseObject("ok", "updated successfully", user));
//            } else {
//                UserEntity user = this.getUserById(userId);
//                user.setFullName(userDTO.getFullName());
//                user.setEmail(userDTO.getEmail());
//                user.setPicture(userDTO.getPicture());
//                user.setStatus(userDTO.getStatus());
//                userRepository.save(user);
//                return ResponseEntity.status(HttpStatus.OK)
//                        .body(new ResponseObject("ok", "updated successfully", user));
//            }
//        }
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new ResponseObject("failed", "updated failed", ""));
//    }
//
//
//    public UserUpdateDTO updateUserProfile(Long userId, UserUpdateDTO userUpdateDTO) {
//
//        Optional<UserEntity> userEntity = userRepository.findById(userId);
//        if (userEntity.isPresent()) {
//            UserEntity user = this.getUserById(userId);
//            user.setFullName(userUpdateDTO.getFullName());
//            user.setEmail(userUpdateDTO.getEmail());
//            user.setPicture(userUpdateDTO.getPicture());
//            userRepository.save(user);
//            return userUpdateDTO;
//        }
//        throw new UserException("updated failed");
//    }
//
    public void updateUserPassword(Long userId, UserPasswordUpdateDTO userPasswordUpdateDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            String encodedPasswordFromDatabase  = userEntity.get().getPassword();
            if (passwordEncoder.matches(userPasswordUpdateDTO.getOldPassword(),encodedPasswordFromDatabase)) {
                if (userPasswordUpdateDTO.getNewPassword().equals(userPasswordUpdateDTO.getConfirmPassword())) {
                    UserEntity user = this.getUserById(userId);
                    user.setHashedpassword(encoder.encode(userPasswordUpdateDTO.getNewPassword()));
                    userRepository.save(user);
                }else throw new UserException("confirm password is not correct");
            } else throw new UserException("old password is not correct ");
        } else throw new UserException("updated failed");
    }

    public void changePassword(Long userId, UserPasswordUpdateDTO userPasswordUpdateDTO){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            String encodedPasswordFromDatabase  = userEntity.get().getPassword();
            if (passwordEncoder.matches(userPasswordUpdateDTO.getNewPassword(), encodedPasswordFromDatabase)) {
                throw new UserException("New password must be different from the old one");
            } else {
                UserEntity user = userEntity.get();
                user.setHashedpassword(passwordEncoder.encode(userPasswordUpdateDTO.getNewPassword()));
                userRepository.save(user);
            }
        } else {
            throw new UserException("User not found");
        }
    }
    public void changePasswordSuper(Long userId, Long otp, UserPasswordUpdateDTO userPasswordUpdateDTO){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            if (otp.equals(userEntity.get().getOTP())){
                String encodedPasswordFromDatabase  = userEntity.get().getPassword();
                if (passwordEncoder.matches(userPasswordUpdateDTO.getNewPassword(), encodedPasswordFromDatabase)) {
                    throw new UserException("New password must be different from the old one");
                } else {
                    UserEntity user = userEntity.get();
                    user.setHashedpassword(passwordEncoder.encode(userPasswordUpdateDTO.getNewPassword()));
                    userRepository.save(user);
                }
            }else throw new UserException("OTP is not matches");
        } else {
            throw new UserException("User not found");
        }
    }


    public String validMail(String mail) {
        if (mail.equals("techupmoment@gmail.com")) {
            UserEntity user = userRepository.findByMail(mail);
            if (user != null) {
                Random random = new Random();
                long otp = 100000 + random.nextInt(900000);
                user.setOTP(otp);
                userRepository.save(user);

                // Gửi email với mã OTP
                sendEmail(mail, "OTP for validation", "Your OTP is: " + otp);

                return "Success";
            } else return "Not found user";
        } else return "Email does not match";
    }
    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public void delete(Long id){
        Optional<UserEntity> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
    }

//    public void setRole(UserEntity user, String role) {
//        RoleEntity roleEntity = roleRepository.findByName(role);
//        if (roleEntity != null) {
//            Set<RoleEntity> roleEntities = new HashSet<>();
//            RoleEntity userRole = roleRepository.findByName(role);
//            roleEntities.add(userRole);
//            user.setRoles(roleEntities);
//
//        } else throw new UserException("Role doesn't exists!");
//    }



}
