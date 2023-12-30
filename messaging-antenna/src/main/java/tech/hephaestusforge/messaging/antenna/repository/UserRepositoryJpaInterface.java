package tech.hephaestusforge.messaging.antenna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.hephaestusforge.messaging.antenna.model.datasource.UserModel;

public interface UserRepositoryJpaInterface extends JpaRepository<UserModel, String>, UserRepositoryInterface {
}
