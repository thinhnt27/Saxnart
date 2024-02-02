package com.saxnart.Saxnart.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
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
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date showDate;

    @Column(columnDefinition = "LONGTEXT")
    private String picture;

    @Column
    private String author;

    @Column
    private Boolean isSpecial;

    @Column(columnDefinition = "LONGTEXT")
    private String content;


    @OneToMany(mappedBy = "showtime")
    @JsonIgnore
    private Set<BookingEntity> bookings = new HashSet<>();

    @OneToMany(mappedBy = "showtime",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<TicketTypeEntity> ticketShows = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ShowEntity showEntity = (ShowEntity) obj;
        return Objects.equals(id, showEntity.getId());
    }

}
