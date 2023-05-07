package com.tenpo.challenge.record.application;

import com.tenpo.challenge.exception.domain.ResourceNotFoundException;
import com.tenpo.challenge.record.domain.Record;
import com.tenpo.challenge.record.domain.RecordRepository;
import com.tenpo.challenge.shared.domain.PaginatedResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RecordService {

    private final RecordRepository repository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RecordService(RecordRepository repository) {
        this.repository = repository;
    }

    public Record save(Record record) {
        logger.info("Saving record: {}", record);
        Record result = repository.save(record);
        logger.info("Record saved {}", record);
        return result;
    }

    public Record get(String id) {
        logger.info("Getting record | Id: {}", id);
        Record record = repository.get(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Record with id: %s does not exist", id)));
        logger.info("Record Found: {}", record);
        return record;
    }

    public List<Record> getAll() {
        logger.info("Getting records");
        List<Record> records = repository.getAll();
        logger.info("Records Found: {}", records);
        return records;
    }

    public PaginatedResult<Record> getAllPaginated(int pageNumber, int pageSize) {
        logger.info("Getting paginated records");
        PaginatedResult<Record> paginatedRecords = repository.getAllPaginated(pageNumber, pageSize);
        logger.info("Paginated Records Found: {}", paginatedRecords);
        return paginatedRecords;
    }

    public void delete(String id) {
        logger.info("Deleting record | id: {}", id);
        repository.delete(id);
        logger.info("Record Deleted | id: {}", id);
    }
}
