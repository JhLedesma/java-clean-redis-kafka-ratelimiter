package com.tenpo.challenge.record.domain;

import com.tenpo.challenge.shared.domain.PaginatedResult;
import com.tenpo.challenge.shared.domain.Repository;

public interface RecordRepository extends Repository<Record, String> {

    public PaginatedResult<Record> getAllPaginated(int pageNumber, int pageSize);
}
