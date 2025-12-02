package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.RestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public Restaurant save(Restaurant restaurant){
        RestaurantEntity entity = restaurantEntityMapper.toEntity(restaurant);
        return restaurantEntityMapper.toRestaurant(restaurantRepository.save(entity));
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantEntityMapper::toRestaurant)
                .orElse(null);
    }

}
