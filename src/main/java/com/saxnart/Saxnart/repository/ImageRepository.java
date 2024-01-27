package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    List<ImageEntity> findByOrderByCreatedDateDesc();
}
