package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IObjectServicePort;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.domain.spi.IObjectPersistencePort;

import java.util.List;

public class ObjectUseCase implements IObjectServicePort {

    private final IObjectPersistencePort objectPersistencePort;

    public ObjectUseCase(IObjectPersistencePort objectPersistencePort) {
        this.objectPersistencePort = objectPersistencePort;
    }

    @Override
    public void saveObject(User objectModel) {
        objectPersistencePort.saveObject(objectModel);
    }

    @Override
    public List<User> getAllObjects() {
        return objectPersistencePort.getAllObjects();
    }
}