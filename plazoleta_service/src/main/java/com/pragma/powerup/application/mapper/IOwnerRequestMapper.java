package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.OwnerRequestDto;
import com.pragma.powerup.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOwnerRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roleId", ignore = true)
    User toUser(OwnerRequestDto dto);
}
