package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.ChuyenNgheSiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChuyenNgheSiRepository extends JpaRepository<ChuyenNgheSiEntity, Long> {

}
