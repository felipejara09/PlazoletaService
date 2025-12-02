package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IDishService;
import com.pragma.powerup.domain.api.IRestaurantService;
import com.pragma.powerup.domain.api.IUserService;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.usecase.DishUseCase;
import com.pragma.powerup.domain.usecase.RestaurantUseCase;
import com.pragma.powerup.domain.usecase.UserUseCase;
import com.pragma.powerup.infrastructure.out.jpa.adapter.DishJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.PasswordEncoderAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.DishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.RestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.UserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.DishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.RestaurantRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    @Bean
    public IUserPersistencePort userPersistencePort(UserRepository repository,
                                                    UserEntityMapper mapper) {
        return new UserJpaAdapter(repository, mapper);
    }

    @Bean
    public IPasswordEncoderPort passwordEncoderPort() {
        return new PasswordEncoderAdapter();
    }

    @Bean
    public IUserService userServicePort(IUserPersistencePort userPersistencePort,
                                        IPasswordEncoderPort passwordEncoderPort) {
        return new UserUseCase(userPersistencePort, passwordEncoderPort);
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(RestaurantRepository repository,
                                                                RestaurantEntityMapper mapper) {
        return new RestaurantJpaAdapter(repository, mapper);
    }

    @Bean
    public IRestaurantService restaurantServicePort(IRestaurantPersistencePort restaurantPersistencePort,
                                                    IUserPersistencePort userPersistencePort) {
        return new RestaurantUseCase(restaurantPersistencePort, userPersistencePort);
    }

    @Bean
    public IDishPersistencePort dishPersistencePort(DishRepository dishRepository,
                                                    DishEntityMapper dishEntityMapper) {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public IDishService dishServicePort(IDishPersistencePort dishPersistencePort,
                                        IRestaurantPersistencePort restaurantPersistencePort) {
        return new DishUseCase(dishPersistencePort, restaurantPersistencePort);
    }



}