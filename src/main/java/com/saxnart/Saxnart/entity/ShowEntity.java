package com.saxnart.Saxnart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Showtime")
public class ShowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private Date showDate;

    @Column
    private String picture;

    @OneToMany(mappedBy = "showtime")
    private Set<BookingEntity> bookings = new HashSet<>();

}
