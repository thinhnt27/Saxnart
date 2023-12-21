package com.saxnart.Saxnart.entity;

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
    private int priceInternational;

    @Column
    private int weekdayPrice;

    @Column
    private int weekendPrice;

    @OneToMany(mappedBy = "ticketType")
    private Set<BookingDetailEntity> ticketBookingDetails = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TicketTypeEntity ticketType = (TicketTypeEntity) obj;
        return Objects.equals(id, ticketType.getId());
    }
}
