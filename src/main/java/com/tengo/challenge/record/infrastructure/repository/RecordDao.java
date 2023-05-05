package com.tengo.challenge.record.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordDao extends JpaRepository<RecordEntity, String> {
}
