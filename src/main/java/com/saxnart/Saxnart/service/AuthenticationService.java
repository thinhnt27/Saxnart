package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.auth.AuthenticationRequest;
import com.saxnart.Saxnart.auth.AuthenticationResponse;
import com.saxnart.Saxnart.entity.RoleEntity;
import com.saxnart.Saxnart.entity.UserEntity;
import com.saxnart.Saxnart.repository.RoleCustomRepo;
import com.saxnart.Saxnart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleCustomRepo roleCustomRepo;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        UserEntity user = userRepository.findByUsernameAndStatusTrue(authenticationRequest.getUsername()).orElseThrow();
        String fullname = user.getFullName();
        String password = user.getHashedpassword();
        Long id = user.getId();
        List<RoleEntity> role = null;
        if(user != null){
            role = roleCustomRepo.getRole(user);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Set<RoleEntity> setRole = new HashSet<>();
        role.stream().forEach(r -> setRole.add(new RoleEntity(r.getName())));
        user.setRoles(setRole);
        setRole.stream().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName())));
        var jwtToken = jwtService.generateToken(user, authorities);
        var jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);
        return AuthenticationResponse.builder().token(jwtToken).refreshToken(jwtRefreshToken).build();
    }

}
