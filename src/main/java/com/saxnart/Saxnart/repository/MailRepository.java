package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.MailConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends JpaRepository<MailConfigEntity, Long> {
}
