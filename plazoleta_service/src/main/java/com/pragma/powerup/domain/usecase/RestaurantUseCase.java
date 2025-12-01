package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRestaurantService;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.util.RoleConstants;
import lombok.AllArgsConstructor;



@AllArgsConstructor
public class RestaurantUseCase  implements IRestaurantService {

    private final IRestaurantPersistencePort iRestaurantPersistencePort;
    private final IUserPersistencePort iUserPersistencePort;

   @Override
    public void createRestaurant(Restaurant restaurant){
       if (!restaurant.getPhoneNumber().matches("^\\+?\\d{10,13}$"))
           throw new IllegalArgumentException("Invalid phone number format");

       if (!restaurant.getNitId().matches("^\\d+$"))
           throw new IllegalArgumentException("Invalid NIT format");

       if (restaurant.getName().matches("^\\d+$"))
           throw new IllegalArgumentException("Name cannot contain only numbers; alphanumeric value required");

       User owner = iUserPersistencePort.findById(restaurant.getOwnerId());
       if ( owner == null)
           throw new IllegalArgumentException("The owner does not exist");

       if (owner.getRoleId() == null || owner.getRoleId() != RoleConstants.OWNER_ROLE_ID)
           throw new IllegalArgumentException("User does not have the OWNER role");


       iRestaurantPersistencePort.save(restaurant);
   }

}
