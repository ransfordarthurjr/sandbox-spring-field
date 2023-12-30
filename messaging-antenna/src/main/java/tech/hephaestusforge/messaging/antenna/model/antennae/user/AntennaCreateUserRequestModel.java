package tech.hephaestusforge.messaging.antenna.model.antennae.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class AntennaCreateUserRequestModel {
    private String username;

    private String firstname;
    private String lastname;

    private String phone;

    @SerializedName("job_title")
    @JsonProperty("job_title")
    private String jobTitle;

    private String avatar;
    private String status;
}