package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.IpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpRepository extends JpaRepository<IpEntity, Long> {

}
