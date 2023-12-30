package tech.hephaestusforge.messaging.antenna.model.datasource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "ant_users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @SerializedName("created_at")
    @JsonProperty("created_at")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String username;
    private String email;

    private String realm;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String firstname;
    private String lastname;

    private String phone;

    private String role;

    @SerializedName("job_title")
    @JsonProperty("job_title")
    @Column(name = "job_title")
    private String jobTitle;

    private String avatar;

    private String status;
}
