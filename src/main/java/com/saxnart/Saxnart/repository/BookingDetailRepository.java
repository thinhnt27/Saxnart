package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.BookingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetailEntity, Long> {
    List<BookingDetailEntity> findAllByBookingId(Long id);
}
