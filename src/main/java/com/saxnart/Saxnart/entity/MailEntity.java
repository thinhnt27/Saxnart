package com.saxnart.Saxnart.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Mail")
public class MailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String SMTPHost;

    @Column
    private Long SMTPPort;

    @Column
    private String SMTPUsername;

    @Column
    private String SMTPPassword;

}
