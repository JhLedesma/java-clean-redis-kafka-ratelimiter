package com.tengo.challenge.record.infrastructure.repository;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "record")
@Data
@EntityListeners(AuditingEntityListener.class)
public class RecordEntity {

    @Id
    private String id;
    @Column(nullable = false)
    private String endpoint;
    @Column(name = "creation_date", updatable = false, nullable = false)
    @CreatedDate
    private LocalDateTime creationDate;
    @Column(name = "response_code", nullable = false)
    private int responseCode;
    @Column(name = "response_status", nullable = false)
    private String responseStatus;
    private String response;
}
