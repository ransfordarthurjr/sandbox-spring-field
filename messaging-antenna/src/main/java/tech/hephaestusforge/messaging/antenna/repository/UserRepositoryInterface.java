package tech.hephaestusforge.messaging.antenna.repository;

import tech.hephaestusforge.messaging.antenna.model.datasource.UserModel;

import java.util.Optional;

public interface UserRepositoryInterface {
    Optional<UserModel> findUserByUsername(String username);
}
