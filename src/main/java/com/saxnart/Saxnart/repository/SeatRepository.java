package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    SeatEntity findBySeatNum(String seatNum);

    List<SeatEntity> findAllByBooking_Id(Long id);
}
