package com.saxnart.Saxnart.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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

        @Column
        private String authorModified;

        @Column
        @Temporal(TemporalType.TIMESTAMP)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS", timezone = "Asia/Ho_Chi_Minh")
        private Date dateModified;


}
