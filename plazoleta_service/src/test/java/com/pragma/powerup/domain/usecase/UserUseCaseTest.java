package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private UserUseCase userUseCase;

    private User buildValidUser() {
        User u = new User();
        u.setName("Felipe");
        u.setLastName("Jaramillo");
        u.setDocumentId("123456789");
        u.setPhoneNumber("+573001234567");
        u.setEmail("felipe@test.com");
        u.setPassword("MiClaveSegura");
        u.setBirthDate(LocalDate.now().minusYears(25));
        return u;
    }

    @Test
    void createOwner_success() {
        User user = buildValidUser();

        when(userPersistencePort.findByEmail(user.getEmail())).thenReturn(null);
        when(passwordEncoderPort.encode("MiClaveSegura")).thenReturn("encoded-password");

        userUseCase.createOwner(user);


        assertEquals("encoded-password", user.getPassword());
        assertEquals(2L, user.getRoleId());

        verify(userPersistencePort).save(any(User.class));
    }

    @Test
    void createOwner_duplicatedEmail_throwsException() {
        User user = buildValidUser();
        when(userPersistencePort.findByEmail(user.getEmail())).thenReturn(new User());

        assertThrows(IllegalArgumentException.class,
                () -> userUseCase.createOwner(user));

        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void createOwner_invalidPhone_throwsException() {
        User user = buildValidUser();
        user.setPhoneNumber("123");

        assertThrows(IllegalArgumentException.class,
                () -> userUseCase.createOwner(user));

        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void createOwner_invalidDocument_throwsException() {
        User user = buildValidUser();
        user.setDocumentId("ABC123");

        assertThrows(IllegalArgumentException.class,
                () -> userUseCase.createOwner(user));

        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void createOwner_underAge_throwsException() {
        User user = buildValidUser();
        user.setBirthDate(LocalDate.now().minusYears(17));

        assertThrows(IllegalArgumentException.class,
                () -> userUseCase.createOwner(user));

        verify(userPersistencePort, never()).save(any());
    }
}
