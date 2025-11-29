package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.OwnerResponseDto;
import com.pragma.powerup.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IObjectResponseMapper {
    OwnerResponseDto toResponse(User objectModel);

    List<OwnerResponseDto> toResponseList(List<User> objectModelList);
}
