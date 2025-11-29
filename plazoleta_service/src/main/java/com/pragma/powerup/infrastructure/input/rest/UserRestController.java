package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.OwnerRequestDto;
import com.pragma.powerup.application.dto.response.OwnerResponseDto;
import com.pragma.powerup.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(
            summary = "Crear propietario",
            description = "Permite al usuario Administrador crear la cuenta de un propietario de restaurante."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propietario creado correctamente",
                    content = @Content(schema = @Schema(implementation = OwnerResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Correo ya registrado",
                    content = @Content)
    })
    @PostMapping("/owners")
    public ResponseEntity<OwnerResponseDto> createOwner(
            @Valid @RequestBody OwnerRequestDto ownerRequestDto) {

        OwnerResponseDto response = userHandler.createOwner(ownerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}