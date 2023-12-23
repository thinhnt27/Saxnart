package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.dto.response.SeatResponse;
import com.saxnart.Saxnart.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
//    SeatEntity findBySeatNum(String seatNum);
//
//    List<SeatEntity> findAllByBooking_Id(Long id);

    @Query("SELECT s " +
            "FROM SeatEntity s " +
            "JOIN BookingSeatEntity bs " +
            "JOIN BookingEntity b " +
            "WHERE b.showtime.id = :showtimeId AND b.status = true AND bs.booking.id = b.id AND s.id = bs.seat.id")
    List<SeatEntity> findBookedSeatsByShowtimeId(Long showtimeId);

}
