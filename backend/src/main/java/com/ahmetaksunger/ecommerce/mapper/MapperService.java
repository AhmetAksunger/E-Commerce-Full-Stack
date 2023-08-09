package com.ahmetaksunger.ecommerce.mapper;

import org.modelmapper.ModelMapper;

public interface MapperService {
    ModelMapper forRequest();
    ModelMapper forResponse();
}
