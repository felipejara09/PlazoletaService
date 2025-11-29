package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IUserService;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.usecase.UserUseCase;
import com.pragma.powerup.infrastructure.out.jpa.adapter.PasswordEncoderAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.UserEntityMapper;
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
}