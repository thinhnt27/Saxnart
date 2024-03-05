package com.saxnart.Saxnart.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Email {
    @NotBlank(message = "Email can not be bank")
    private String toEmail;

    @NotBlank(message = "Subject can not be bank")
    private String subject;

    @NotBlank(message = "Body can not be bank")
    private String body;
}
