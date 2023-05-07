package com.tenpo.challenge.record.infrastructure.controller;

import com.tenpo.challenge.record.application.RecordService;
import com.tenpo.challenge.record.domain.Record;
import com.tenpo.challenge.shared.domain.PaginatedResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/v1")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/records")
    public PaginatedResult<Record> getAllPaginated(
            @RequestParam(defaultValue = "0") @Positive(message = "Page number must be positive") int pageNumber,
            @RequestParam(defaultValue = "5") @Positive(message = "Page size must be positive") int pageSize
    ) {
        return recordService.getAllPaginated(pageNumber, pageSize);
    }
}
