package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.DishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final DishRepository dishRepository;
    private final DishEntityMapper dishEntityMapper;


    @Override
    public Dish save(Dish dish) {
        DishEntity entity = dishEntityMapper.toEntity(dish);
        DishEntity saved = dishRepository.save(entity);
        return dishEntityMapper.toDish(saved);
    }


}
