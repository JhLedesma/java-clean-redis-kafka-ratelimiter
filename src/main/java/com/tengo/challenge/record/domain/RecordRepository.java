package com.tengo.challenge.record.domain;

import com.tengo.challenge.shared.domain.PaginatedResult;
import com.tengo.challenge.shared.domain.Repository;

public interface RecordRepository extends Repository<Record, String> {

    public PaginatedResult<Record> getAllPaginated(int pageNumber, int pageSize);
}
