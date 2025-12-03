package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IDishService;
import com.pragma.powerup.domain.exception.DomainException;
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

        if (dish.getName() == null || dish.getName().isBlank()   ){
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
        var restaurant = iRestaurantPersistencePort.findById(dish.getRestaurantId());
        if (restaurant == null){
            throw new IllegalArgumentException("The restaurant associated with the dish does not exist");
        }
        if (!restaurant.getOwnerId().equals(ownerId)){
            throw new IllegalArgumentException("The owner is not authorized to create dishes in this restaurant");
        }

        dish.setActive(Boolean.TRUE);

        iDishPersistencePort.save(dish);

    }

    @Override

    public void updateDish(Long ownerId, Dish dish) {

        if (dish.getId() == null) {
            throw new DomainException("Dish ID is required");
        }

        Dish existingDish = iDishPersistencePort.findById(dish.getId());
        if (existingDish == null) {
            throw new DomainException("Dish not found");
        }

        var restaurant = iRestaurantPersistencePort.findById(existingDish.getRestaurantId());
        if (restaurant == null) {
            throw new DomainException("Restaurant not found");
        }

        if (!restaurant.getOwnerId().equals(ownerId)) {
            throw new DomainException("The owner is not authorized to update dishes in this restaurant");
        }


        if (dish.getPrice() != null) {
            if (dish.getPrice() <= 0) {
                throw new DomainException("Price must be positive");
            }
            existingDish.setPrice(dish.getPrice());
        }

        if (dish.getDescription() != null && !dish.getDescription().isBlank()) {
            existingDish.setDescription(dish.getDescription());
        }

        iDishPersistencePort.save(existingDish);
    }

    @Override
    public Dish findById(Long dishId) {
        return iDishPersistencePort.findById(dishId);
    }


}
