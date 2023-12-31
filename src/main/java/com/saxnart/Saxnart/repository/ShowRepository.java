package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<ShowEntity, Long> {
    Optional<ShowEntity> findById(Long id);

    List<ShowEntity> findByIsSpecialIsTrue();

    List<ShowEntity> findByIsSpecialIsFalse();

    @Query("SELECT s FROM ShowEntity s WHERE DATE(s.showDate) >= :currentDate AND s.isSpecial = true")
    List<ShowEntity> findSpecialTrueShowsAfterCurrentDate(@Param("currentDate") String currentDate);

    @Query("SELECT s FROM ShowEntity s WHERE DATE(s.showDate) >= :currentDate AND s.isSpecial = false")
    List<ShowEntity> findSpecialFalseShowsAfterCurrentDate(@Param("currentDate") String currentDate);

}
