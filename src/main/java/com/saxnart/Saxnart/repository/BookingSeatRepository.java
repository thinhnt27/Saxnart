package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.BookingSeatEntity;
import com.saxnart.Saxnart.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeatEntity, Long> {
    List<BookingSeatEntity> findByBooking_Id(Long id);
}
