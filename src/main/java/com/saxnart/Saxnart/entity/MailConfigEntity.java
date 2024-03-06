package com.saxnart.Saxnart.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Mail")
public class MailConfigEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column
        private String host;

        @Column
        private int port;

        @Column
        private String username;

        @Column
        private String password;

        @Column
        private Boolean smtpAuth;

        @Column
        private Boolean smtpStartTlsEnable;


}
