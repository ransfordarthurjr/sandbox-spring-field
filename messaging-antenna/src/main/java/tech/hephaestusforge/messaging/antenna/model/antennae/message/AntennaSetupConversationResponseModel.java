package tech.hephaestusforge.messaging.antenna.model.antennae.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class AntennaSetupConversationResponseModel {
    @Id
    private String id;
    private LocalDateTime timestamp;

    @JsonProperty("group_conversation")
    @SerializedName("group_conversation")
    private String groupConversation;
}