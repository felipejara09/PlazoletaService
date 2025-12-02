package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    private DishUseCase dishUseCase;

    @BeforeEach
    void setUp() {
        dishUseCase = new DishUseCase(dishPersistencePort, restaurantPersistencePort);
    }

    private Dish buildValidDish() {
        Dish dish = new Dish();
        dish.setName("Hamburguesa 220");
        dish.setPrice(27000);
        dish.setDescription("Hamburguesa doble carne con queso");
        dish.setImageUrl("https://imagenes.com/hamburguesa.png");
        dish.setCategory("Hamburguesas");
        dish.setRestaurantId(1L);
        return dish;
    }

    private Restaurant buildRestaurant(Long id, Long ownerId) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setOwnerId(ownerId);
        return restaurant;
    }

    @Test
    void createDish_ShouldSaveDish_WhenInputIsValid() {

        Long ownerId = 10L;
        Long restaurantId = 1L;
        Dish dish = buildValidDish();
        dish.setRestaurantId(restaurantId);

        Restaurant restaurant = buildRestaurant(restaurantId, ownerId);

        when(restaurantPersistencePort.findById(restaurantId)).thenReturn(restaurant);


        dishUseCase.createDish(ownerId, dish);


        assertEquals(Boolean.TRUE, dish.getActive(), "Dish must be created as active = true");
        verify(restaurantPersistencePort).findById(restaurantId);
        verify(dishPersistencePort).save(dish);
        verifyNoMoreInteractions(dishPersistencePort, restaurantPersistencePort);
    }

    @Test
    void createDish_ShouldThrow_WhenNameIsNullOrBlank() {
        Dish dish = buildValidDish();
        dish.setName("   ");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> dishUseCase.createDish(10L, dish)
        );

        assertEquals("Dish name is required", ex.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void createDish_ShouldThrow_WhenPriceIsNull() {
        Dish dish = buildValidDish();
        dish.setPrice(null);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> dishUseCase.createDish(10L, dish)
        );

        assertEquals("Dish price is required", ex.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void createDish_ShouldThrow_WhenPriceIsNotPositive() {
        Dish dish = buildValidDish();
        dish.setPrice(0);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> dishUseCase.createDish(10L, dish)
        );

        assertEquals("Dish price must be a positive integer greater than 0", ex.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void createDish_ShouldThrow_WhenDescriptionIsNull() {
        Dish dish = buildValidDish();
        dish.setDescription(null);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> dishUseCase.createDish(10L, dish)
        );

        assertEquals("Dish description is required", ex.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void createDish_ShouldThrow_WhenImageUrlIsNull() {
        Dish dish = buildValidDish();
        dish.setImageUrl(null);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> dishUseCase.createDish(10L, dish)
        );

        assertEquals("Dish image URL is required", ex.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void createDish_ShouldThrow_WhenCategoryIsNull() {
        Dish dish = buildValidDish();
        dish.setCategory(null);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> dishUseCase.createDish(10L, dish)
        );

        assertEquals("Dish category is required", ex.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void createDish_ShouldThrow_WhenRestaurantIdIsNull() {
        Dish dish = buildValidDish();
        dish.setRestaurantId(null);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> dishUseCase.createDish(10L, dish)
        );

        assertEquals("Restaurant ID is required", ex.getMessage());
        verifyNoInteractions(dishPersistencePort, restaurantPersistencePort);
    }

    @Test
    void createDish_ShouldThrow_WhenRestaurantDoesNotExist() {
        Long ownerId = 10L;
        Dish dish = buildValidDish();
        dish.setRestaurantId(99L);

        when(restaurantPersistencePort.findById(99L)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> dishUseCase.createDish(ownerId, dish)
        );

        assertEquals("The restaurant associated with the dish does not exist", ex.getMessage());
        verify(restaurantPersistencePort).findById(99L);
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void createDish_ShouldThrow_WhenOwnerIsNotRestaurantOwner() {
        Long ownerId = 10L;
        Long restaurantId = 1L;

        Dish dish = buildValidDish();
        dish.setRestaurantId(restaurantId);

        Restaurant restaurant = buildRestaurant(restaurantId, 20L);

        when(restaurantPersistencePort.findById(restaurantId)).thenReturn(restaurant);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> dishUseCase.createDish(ownerId, dish)
        );

        assertEquals("The owner is not authorized to create dishes in this restaurant", ex.getMessage());
        verify(restaurantPersistencePort).findById(restaurantId);
        verifyNoInteractions(dishPersistencePort);
    }
}
