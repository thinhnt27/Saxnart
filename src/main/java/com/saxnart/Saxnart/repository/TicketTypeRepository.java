package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.TicketTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketTypeEntity, Long> {
    Optional<TicketTypeEntity> findById(Long id);

    List<TicketTypeEntity> findByShowtime_Id(Long id);
}
