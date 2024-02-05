package com.saxnart.Saxnart.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TicketType")
public class TicketTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer price;


    @OneToMany(mappedBy = "ticketType")
    @JsonIgnore
    private Set<BookingDetailEntity> ticketBookingDetails = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private ShowEntity showtime;

//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//        TicketTypeEntity ticketType = (TicketTypeEntity) obj;
//        return Objects.equals(id, ticketType.getId());
//    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price); // Include all relevant fields
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TicketTypeEntity ticketType = (TicketTypeEntity) obj;
        return Objects.equals(id, ticketType.getId()) &&
                Objects.equals(name, ticketType.getName()) &&
                Objects.equals(price, ticketType.getPrice()); // Include all relevant fields
    }




}
