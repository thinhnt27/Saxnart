package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByShowtime_Id(Long showTimeId);

    List<BookingEntity> findByStatusIsTrueAndShowtime_Id(Long showTimeId);

    BookingEntity findByEmailAndShowtime_IdAndNameAndCreatedDate(String email, Long showTimeID, String name, Date creatDate);


}
