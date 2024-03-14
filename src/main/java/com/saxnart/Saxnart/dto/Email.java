package com.saxnart.Saxnart.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Email {

    private String toEmail;

    private String subject;

    private String body;
}
