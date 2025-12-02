package com.pragma.powerup.infrastructure.out.jpa.mapper;


import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DishEntityMapper {

    DishEntity toEntity(Dish dish);

    Dish toDish(DishEntity entity);
}
