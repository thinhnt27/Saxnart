package com.saxnart.Saxnart.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Event")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column
    private String showTimeDate;

    @Column(columnDefinition = "LONGTEXT")
    private String image;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "status")
    private Boolean status;
}
