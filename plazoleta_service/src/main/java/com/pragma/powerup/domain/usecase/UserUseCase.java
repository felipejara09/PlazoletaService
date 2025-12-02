package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserService;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.util.RoleConstants;
import lombok.AllArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
public class UserUseCase implements IUserService {

    private final IUserPersistencePort persistence;
    private final IPasswordEncoderPort passwordEncoder;


    @Override
    public void createOwner(User user) {
        if (!user.getEmail().contains("@"))
            throw new IllegalArgumentException("Invalid email format");

        if (persistence.findByEmail(user.getEmail()) != null)
            throw new IllegalArgumentException("Email already exists");

        if (!user.getPhoneNumber().matches("^\\+?\\d{10,13}$"))
            throw new IllegalArgumentException("Invalid phone number format");

        if (!user.getDocumentId().matches("^\\d+$"))
            throw new IllegalArgumentException("Invalid document ID format");

        if (user.getBirthDate().isAfter(LocalDate.now().minusYears(18))) {
            throw new IllegalArgumentException("User must be at least 18 years old");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleId(RoleConstants.OWNER_ROLE_ID);

        persistence.save(user);
    }

}