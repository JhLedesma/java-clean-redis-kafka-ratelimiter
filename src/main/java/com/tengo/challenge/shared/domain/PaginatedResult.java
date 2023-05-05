package com.tengo.challenge.shared.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginatedResult<T> {
    private List<T> results;
    private int totalElements;
    private int pageNumber;
    private int pageSize;
}
