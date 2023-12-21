package com.saxnart.Saxnart.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String fullName;
    private String username;
    private String email;
    private String password;
    private String picture = null;
    private Set<String> role;
}
