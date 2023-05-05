package com.tengo.challenge.shared.domain;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

    public T save(T object);
    public Optional<T> get(ID id);
    public List<T> getAll();
    public void delete(ID id);
}
