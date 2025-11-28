package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.User;
import java.util.List;

public interface IObjectPersistencePort {
    User saveObject(User objectModel);

    List<User> getAllObjects();
}