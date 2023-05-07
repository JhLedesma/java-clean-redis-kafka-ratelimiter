package com.tenpo.challenge.shared.domain;

public interface ModelMapper {
    <S, D> D map(S source, Class<D> destinationType);
}
