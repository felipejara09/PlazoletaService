package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Dish;

public interface IDishService {

    void createDish(Long ownerId, Dish dish);
    void updateDish(Long ownerId, Dish dish);
    Dish findById(Long dishId);
}
