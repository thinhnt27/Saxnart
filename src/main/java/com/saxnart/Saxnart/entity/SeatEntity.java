package com.saxnart.Saxnart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Seat")
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_num", length = 15)
    private String seatNum;

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private Boolean isBook = false;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "book_id")
    private BookingEntity booking;

    @Override
    public int hashCode() {
        return Objects.hash(id, seatNum);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SeatEntity seat = (SeatEntity) obj;
        return Objects.equals(id, seat.getId())
                && Objects.equals(seatNum, seat.getSeatNum());
    }
}

