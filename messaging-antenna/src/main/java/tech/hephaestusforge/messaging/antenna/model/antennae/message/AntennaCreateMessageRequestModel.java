package tech.hephaestusforge.messaging.antenna.model.antennae.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AntennaCreateMessageRequestModel {
    @JsonProperty("conversation_id")
    @SerializedName("conversation_id")
    private String conversationId;
    private String sender;
    private String message;
}
