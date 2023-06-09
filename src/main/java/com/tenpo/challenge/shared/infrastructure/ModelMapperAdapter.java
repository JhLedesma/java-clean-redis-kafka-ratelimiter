package com.tenpo.challenge.shared.infrastructure;

import org.modelmapper.ModelMapper;

public class ModelMapperAdapter implements com.tenpo.challenge.shared.domain.ModelMapper {
    private final ModelMapper modelMapper;

    public ModelMapperAdapter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public <S, D> D map(S source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }
}