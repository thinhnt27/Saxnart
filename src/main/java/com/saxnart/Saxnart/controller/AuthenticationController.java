package com.saxnart.Saxnart.controller;


import com.saxnart.Saxnart.auth.AuthenticationRequest;
import com.saxnart.Saxnart.auth.AuthenticationResponse;
import com.saxnart.Saxnart.auth.MessageResponse;
import com.saxnart.Saxnart.auth.SignupRequest;
import com.saxnart.Saxnart.dto.request.UserPasswordUpdateDTO;
import com.saxnart.Saxnart.entity.RoleEntity;
import com.saxnart.Saxnart.entity.UserEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.repository.RoleCustomRepo;
import com.saxnart.Saxnart.repository.RoleRepository;
import com.saxnart.Saxnart.repository.UserRepository;
import com.saxnart.Saxnart.service.AuthenticationService;
import com.saxnart.Saxnart.service.JwtService;
import com.saxnart.Saxnart.service.UserService;
import com.saxnart.Saxnart.utility.TokenChecker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.util.*;


@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;

    @Autowired
    RoleCustomRepo roleCustomRepo;


    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody AuthenticationRequest authenticationRequest) {
        if (authenticationRequest.getIpLogin().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("error", "Must have ip", ""));
        }
        if (!isValidIPAddress(authenticationRequest.getIpLogin())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("error", "invalid ip", ""));
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<UserEntity> o_user = userRepository.findByUsernameAndStatusTrue(authenticationRequest.getUsername());
        if (o_user.isPresent()) {
            String encodedPasswordFromDatabase = o_user.get().getPassword();
            if (!passwordEncoder.matches(authenticationRequest.getPassword(), encodedPasswordFromDatabase)) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("error", "Username or password is wrong!", ""));
            } else {
                o_user.get().setIpLogin(authenticationRequest.getIpLogin());
                o_user.get().setTimeLogin(new Date());
                userRepository.save(o_user.get());
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "Login Success!", authenticationService.authenticate(authenticationRequest)));
            }
        } else
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("error", "Username or password is wrong!", ""));

    }

    private static boolean isValidIPAddress(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            return inetAddress instanceof java.net.Inet4Address || inetAddress instanceof java.net.Inet6Address;
        } catch (Exception ex) {
            return false;
        }
    }

    @PostMapping("/signup")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        UserEntity user = new UserEntity(signUpRequest.getFullName(),
                signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                true);
        Set<RoleEntity> roleEntities = new HashSet<>();
        RoleEntity userRole = roleRepository.findByName("ADMIN");
        roleEntities.add(userRole);
        user.setRoles(roleEntities);
        userRepository.save(user);
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(signUpRequest.getUsername(), signUpRequest.getPassword(), null);
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<?> getNewToken(@RequestHeader("Authorization") String refreshToken) {
        String username = jwtService.extractTokenToGetUser(refreshToken.substring(7));
        if (username != null) {
            UserEntity user = userRepository.findByUsernameAndStatusTrue(username).orElseThrow();
            List<RoleEntity> role = null;
            if (user != null) {
                role = roleCustomRepo.getRole(user);
            }
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Set<RoleEntity> set = new HashSet<>();
            role.stream().forEach(c -> set.add(new RoleEntity(c.getName())));
            user.setRoles(set);
            set.stream().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName())));
            var jwtToken = jwtService.generateToken(user, authorities);
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setToken(jwtToken);
            authenticationResponse.setRefreshToken(refreshToken.substring(7));
            return ResponseEntity.ok(authenticationResponse);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Can not have new token!!!"));
    }

    @PatchMapping("/updateUserPassword/{userId}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<ResponseObject> updateUserPassword(@RequestHeader("Authorization") String token,
                                                             @PathVariable Long userId,
                                                             @RequestBody UserPasswordUpdateDTO userPasswordUpdateDTO) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", "User ID cannot be null", ""));
        }
        try {
            if (TokenChecker.checkToken(token)) {
                userService.updateUserPassword(userId, userPasswordUpdateDTO);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "found", ""));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", "not found", ""));
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", ex.getMessage(), ""));
        }
    }

    @PostMapping("/logout/{userId}")
    public ResponseEntity<ResponseObject> logout(@PathVariable Long userId,
                                                 @RequestBody AuthenticationRequest authenticationRequest) {
        Optional<UserEntity> o_user = userRepository.findByUsernameAndStatusTrue(authenticationRequest.getUsername());
        if(o_user.isPresent()){
            if(userId.equals(o_user.get().getId())){
                o_user.get().setTimeLogout(new Date());
                userRepository.save(o_user.get());
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "Success", ""));
            }else
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "Id not matches", ""));
        }else  return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Not found", ""));
    }

}
