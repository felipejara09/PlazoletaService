package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.application.dto.response.DishResponseDto;
import com.pragma.powerup.application.handler.IDishHandler;
import com.pragma.powerup.application.mapper.IDishRequestMapper;
import com.pragma.powerup.application.mapper.IDishResponseMapper;
import com.pragma.powerup.domain.api.IDishService;
import com.pragma.powerup.domain.model.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler  implements IDishHandler {

    private final IDishService dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final IDishResponseMapper dishResponseMapper;



    @Override
    public DishResponseDto createDish(DishRequestDto dto) {
        Dish dish = dishRequestMapper.toDish(dto);

        dishServicePort.createDish(dto.getOwnerId(), dish);

        return new DishResponseDto(
                dish.getId(),
                dish.getName(),
                dish.getPrice(),
                dish.getActive()
        );
    }

    @Override
    public DishResponseDto updateDish(DishUpdateRequestDto dto) {

        Dish dish = new Dish();
        dish.setId(dto.getDishId());
        dish.setPrice(dto.getPrice());
        dish.setDescription(dto.getDescription());

        dishServicePort.updateDish(dto.getOwnerId(), dish);

        Dish updated = dishServicePort.findById(dto.getDishId());

        return dishResponseMapper.toDishResponseDto(updated);
    }

}
