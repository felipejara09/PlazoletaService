package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.OwnerRequestDto;
import com.pragma.powerup.application.dto.response.OwnerResponseDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.application.mapper.IOwnerRequestMapper;
import com.pragma.powerup.domain.api.IUserService;
import com.pragma.powerup.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserService  userServicePort;
    private final IOwnerRequestMapper ownerRequestMapper;

    @Override
    public OwnerResponseDto createOwner(OwnerRequestDto ownerRequestDto) {

        User user = ownerRequestMapper.toUser(ownerRequestDto);
        userServicePort.createOwner(user);

        return new OwnerResponseDto(
                user.getId(),
                user.getName() + " " + user.getLastName(),
                user.getEmail()
        );
    }
}