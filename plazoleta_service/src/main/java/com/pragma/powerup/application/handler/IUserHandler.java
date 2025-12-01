package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.OwnerRequestDto;
import com.pragma.powerup.application.dto.response.OwnerResponseDto;


public interface IUserHandler {

    OwnerResponseDto createOwner(OwnerRequestDto ownerRequestDto);
}