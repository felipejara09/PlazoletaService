package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.util.RoleConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RestaurantUseCaseTest {

private IRestaurantPersistencePort restaurantPersistencePort;
private IUserPersistencePort userPersistencePort;
private RestaurantUseCase restaurantUseCase;

@BeforeEach
void setUp() {
    restaurantPersistencePort = mock(IRestaurantPersistencePort.class);
    userPersistencePort = mock(IUserPersistencePort.class);
    restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, userPersistencePort);
}

private Restaurant buildValidRestaurant() {
    Restaurant r = new Restaurant();
    r.setName("Rest Felipan 123");
    r.setNitId("123456789");
    r.setAddress("Calle 123 #45-67");
    r.setPhoneNumber("+573001233467");
    r.setLogoUrl("https://logo.com/img.png");
    r.setOwnerId(1L);
    return r;
}

private User buildOwner() {
    User u = new User();
    u.setId(1L);
    u.setRoleId(RoleConstants.OWNER_ROLE_ID);
    return u;
}

@Test
void createRestaurant_success() {
    Restaurant restaurant = buildValidRestaurant();
    User owner = buildOwner();

    when(userPersistencePort.findById(1L)).thenReturn(owner);
    when(restaurantPersistencePort.save(ArgumentMatchers.any(Restaurant.class)))
            .thenReturn(restaurant);

    restaurantUseCase.createRestaurant(restaurant);

    verify(restaurantPersistencePort).save(ArgumentMatchers.any(Restaurant.class));
}

@Test
void createRestaurant_ownerNotFound_throwsException() {
    Restaurant restaurant = buildValidRestaurant();
    when(userPersistencePort.findById(1L)).thenReturn(null);

    assertThrows(IllegalArgumentException.class,
            () -> restaurantUseCase.createRestaurant(restaurant));

    verify(restaurantPersistencePort, never()).save(any());
}

@Test
void createRestaurant_ownerWithWrongRole_throwsException() {
    Restaurant restaurant = buildValidRestaurant();
    User owner = new User();
    owner.setId(1L);
    owner.setRoleId(3L);

    when(userPersistencePort.findById(1L)).thenReturn(owner);

    assertThrows(IllegalArgumentException.class,
            () -> restaurantUseCase.createRestaurant(restaurant));

    verify(restaurantPersistencePort, never()).save(any());
}

@Test
void createRestaurant_invalidPhone_throwsException() {
    Restaurant restaurant = buildValidRestaurant();
    restaurant.setPhoneNumber("123456789012345");

    when(userPersistencePort.findById(1L)).thenReturn(buildOwner());

    assertThrows(IllegalArgumentException.class,
            () -> restaurantUseCase.createRestaurant(restaurant));

    verify(restaurantPersistencePort, never()).save(any());
}

@Test
void createRestaurant_invalidNit_throwsException() {
    Restaurant restaurant = buildValidRestaurant();
    restaurant.setNitId("ABC123");

    when(userPersistencePort.findById(1L)).thenReturn(buildOwner());

    assertThrows(IllegalArgumentException.class,
            () -> restaurantUseCase.createRestaurant(restaurant));

    verify(restaurantPersistencePort, never()).save(any());
}

@Test
void createRestaurant_nameOnlyNumbers_throwsException() {
    Restaurant restaurant = buildValidRestaurant();
    restaurant.setName("123456");

    when(userPersistencePort.findById(1L)).thenReturn(buildOwner());

    assertThrows(IllegalArgumentException.class,
            () -> restaurantUseCase.createRestaurant(restaurant));

    verify(restaurantPersistencePort, never()).save(any());
}
}
