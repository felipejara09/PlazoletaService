package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IDishService;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DishUseCase implements IDishService {

    private final IDishPersistencePort iDishPersistencePort;
    private final IRestaurantPersistencePort iRestaurantPersistencePort;

    @Override
    public void createDish(Long ownerId, Dish dish){

        if (dish.getName() == null){
            throw new IllegalArgumentException("Dish name is required");
        }

        if (dish.getPrice() == null){
            throw new IllegalArgumentException("Dish price is required");
        }
        if (dish.getPrice() <= 0){
            throw new IllegalArgumentException("Dish price must be a positive integer greater than 0");
        }

        if (dish.getDescription() == null){
            throw new IllegalArgumentException("Dish description is required");
        }

        if (dish.getImageUrl() == null) {
            throw new IllegalArgumentException("Dish image URL is required");
        }
        if (dish.getCategory() == null) {
            throw new IllegalArgumentException("Dish category is required");
        }
        if (dish.getRestaurantId() == null) {
            throw new IllegalArgumentException("Restaurant ID is required");
        }
        if (iRestaurantPersistencePort.findById(dish.getRestaurantId()) == null){
            throw new IllegalArgumentException("The restaurant associated with the dish does not exist");
        }
        if (!(iRestaurantPersistencePort.findById(dish.getRestaurantId())).getOwnerId().equals(ownerId)){
            throw new IllegalArgumentException("The owner is not authorized to create dishes in this restaurant");
        }

        dish.setActive(Boolean.TRUE);

        iDishPersistencePort.save(dish);



    }

}
