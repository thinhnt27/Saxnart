package com.saxnart.Saxnart.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Booking")
public class BookingEntity {
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
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date createdDate;

    @Column
    private Boolean status;

    @Column
    private Boolean isPayment;

    @Column
    private double totalPrice;

    @OneToMany(mappedBy = "booking")
    private Set<BookingSeatEntity> bookingSeats = new HashSet<>();

    @OneToMany(mappedBy = "booking")
    private Set<BookingDetailEntity> bookingDetails = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "showtime_id")
    private ShowEntity showtime;


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BookingEntity booking = (BookingEntity) obj;
        return Objects.equals(id, booking.getId());
    }

}
