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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
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

    @GetMapping("/getAllUser")
    public List<UserEntity> getAllUser(){
        return userService.getAllUser();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<UserEntity> o_user = userRepository.findByUsernameAndStatusTrue(authenticationRequest.getUsername());
        if(o_user.isPresent()){
            String encodedPasswordFromDatabase  = o_user.get().getPassword();
                if(!passwordEncoder.matches(authenticationRequest.getPassword(),encodedPasswordFromDatabase)){
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username or password is wrong!"));
            }else{
                return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
            }
        }else return ResponseEntity.badRequest().body(new MessageResponse("Error: Username or password is wrong!"));
    }
    @PostMapping("/signup")
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
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(signUpRequest.getUsername(), signUpRequest.getPassword());
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
//   @GetMapping("/logout")
//public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
//       String token = authorizationHeader.substring(7);
//    // Lấy token từ yêu cầu HTTP
//    String token = request.getHeader("Authorization");
//
//    // Xóa token khỏi session
//    SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
//    SecurityContextHolder.getContext().setAuthentication(null);
//    SecurityContextHolder.getContext().getAuthentication().invalidate();
//
//    // Truyền thông với người dùng rằng họ đã đăng xuất
//    return ResponseEntity.ok("Đăng xuất thành công!");
//}
//
//}
}
