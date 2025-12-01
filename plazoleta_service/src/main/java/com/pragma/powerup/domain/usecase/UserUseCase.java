package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserService;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;

import java.time.LocalDate;

public class UserUseCase implements IUserService {

    private final IUserPersistencePort persistence;
    private final IPasswordEncoderPort passwordEncoder;
    private static final Long ROLE_OWNER = 2L;


    public UserUseCase(IUserPersistencePort persistence, IPasswordEncoderPort passwordEncoder) {
        this.persistence = persistence;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createOwner(User user) {
        if (!user.getEmail().contains("@"))
            throw new IllegalArgumentException("Email inválido");

        if (persistence.findByEmail(user.getEmail()) != null)
            throw new IllegalArgumentException("El correo ya existe");

        if (!user.getPhoneNumber().matches("^\\+?\\d{10,13}$"))
            throw new IllegalArgumentException("Teléfono inválido");

        if (!user.getDocumentId().matches("^\\d+$"))
            throw new IllegalArgumentException("Documento inválido");

        if (user.getBirthDate().isAfter(LocalDate.now().minusYears(18))) {
            throw new IllegalArgumentException("El propietario debe ser mayor de edad.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleId(ROLE_OWNER);

        persistence.save(user);
    }

}