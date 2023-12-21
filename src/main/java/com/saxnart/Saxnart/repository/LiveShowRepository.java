package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.LiveShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveShowRepository extends JpaRepository<LiveShowEntity, Long> {
}
