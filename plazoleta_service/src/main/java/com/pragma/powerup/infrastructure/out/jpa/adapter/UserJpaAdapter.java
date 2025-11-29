package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.UserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User save(User user) {
        UserEntity entity = userEntityMapper.toEntity(user);
        return userEntityMapper.toUser(userRepository.save(entity));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toUser)
                .orElse(null);
    }
}