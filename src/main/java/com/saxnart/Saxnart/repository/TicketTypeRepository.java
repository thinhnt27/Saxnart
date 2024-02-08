package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.TicketTypeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketTypeEntity, Long> {
    Optional<TicketTypeEntity> findById(Long id);

    List<TicketTypeEntity> findByShowtime_Id(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM TicketTypeEntity t WHERE t.showtime.id = :showtimeId")
    void deleteByShowtime_Id(Long showtimeId);
}
