package tech.hephaestusforge.messaging.antenna.model.antennae.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.hephaestusforge.messaging.antenna.model.datasource.UserModel;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class AntennaUsersResponseModel {
    private List<UserModel> users;
}