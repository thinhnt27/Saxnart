package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.ImageHomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageHomeRopository extends JpaRepository<ImageHomeEntity, Long> {
    Optional<ImageHomeEntity> findById(Long Id);
}
