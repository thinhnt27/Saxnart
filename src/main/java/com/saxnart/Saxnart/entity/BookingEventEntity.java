package com.saxnart.Saxnart.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "BookingEvent")
public class BookingEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String telephoneNum;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column
    private Date createdDate;

    @Column
    private Date eventDate;

    @Column
    private Boolean status;
}
