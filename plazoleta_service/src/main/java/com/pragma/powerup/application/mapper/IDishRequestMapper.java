package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IDishRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true) // se setea en el use case
    Dish toDish(DishRequestDto dto);
}
