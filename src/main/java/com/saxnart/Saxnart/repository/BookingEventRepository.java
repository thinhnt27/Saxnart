package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.BookingEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingEventRepository extends JpaRepository<BookingEventEntity, Long> {
}
