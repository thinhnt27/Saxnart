package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.BookingSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeatEntity, Long> {
}
