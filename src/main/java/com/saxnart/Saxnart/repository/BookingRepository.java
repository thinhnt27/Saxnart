package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByShowtime_Id(Long showTimeId);

    List<BookingEntity> findByStatusIsTrueAndShowtime_Id(Long showTimeId);


}
