/*
package tech.hephaestusforge.messaging.antenna.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.hephaestusforge.messaging.antenna.model.antennae.user.AntennaCreateUserRequestModel;
import tech.hephaestusforge.messaging.antenna.model.antennae.user.AntennaUsersRequestModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.UserModel;
import tech.hephaestusforge.messaging.antenna.repository.UserRepository;
import tech.hephaestusforge.messaging.antenna.repository.UserRepositoryJpaInterface;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final UserRepositoryJpaInterface jpaRepositoryInterface;

    public UserModel fetchUser(String username) {
        return this.repository.findUserByUsername(username).orElse(null);
    }

    public UserModel createUser(AntennaCreateUserRequestModel request) {
        var usr = UserModel.builder()
                .createdAt(LocalDateTime.now())
                .username(request.getUsername())
                .email(request.getUsername())
                .realm("hephaestus")
                .password("")
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .phone(request.getPhone())
                .role("User")
                .jobTitle(request.getJobTitle())
                .avatar(request.getAvatar())
                .status(request.getStatus())
                .build();

        UserModel user;
        user = this.jpaRepositoryInterface.save(usr);

        return user;
    }

    public List<UserModel> fetchUsers(AntennaUsersRequestModel request) {
        return this.repository.findUsersByUsernames(request.getUsernames());
    }

    public List<UserModel> searchUsers(String searchTerm) {
        return this.repository.searchUsers(searchTerm);
    }
}
*/
