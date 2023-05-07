package com.tenpo.challenge.record.infrastructure.repository;

import com.tenpo.challenge.exception.domain.ChallangeException;
import com.tenpo.challenge.record.domain.Record;
import com.tenpo.challenge.record.domain.RecordRepository;
import com.tenpo.challenge.shared.domain.ModelMapper;
import com.tenpo.challenge.shared.domain.PaginatedResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecordRepositoryAdapter implements RecordRepository {
    private final RecordDao dao;
    private final ModelMapper mapper;

    public RecordRepositoryAdapter(RecordDao dao, ModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }


    @Override
    public Record save(Record record) {
        return Optional.of(mapper.map(record, RecordEntity.class))
                .map(dao::save)
                .map(entity -> mapper.map(entity, Record.class))
                .orElseThrow(() -> new ChallangeException("Error saving record: " + record));
    }

    @Override
    public Optional<Record> get(String id) {
        return dao.findById(id).map(entity -> mapper.map(entity, Record.class));
    }

    @Override
    public List<Record> getAll() {
        return dao.findAll()
                .stream()
                .map(entity -> mapper.map(entity, Record.class))
                .collect(Collectors.toList());
    }

    @Override
    public PaginatedResult<Record> getAllPaginated(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        final List<Record> records = dao.findAll(pageable).getContent().stream().map(entity -> mapper.map(entity, Record.class)).collect(Collectors.toList());
        return new PaginatedResult<Record>(records, records.size(), pageNumber, pageSize);
    }

    @Override
    public void delete(String id) {
        dao.deleteById(id);
    }
}
