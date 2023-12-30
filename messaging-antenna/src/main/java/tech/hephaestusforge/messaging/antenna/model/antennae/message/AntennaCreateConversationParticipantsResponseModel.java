package tech.hephaestusforge.messaging.antenna.model.antennae.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class AntennaCreateConversationParticipantsResponseModel {
    @SerializedName("conversation_id")
    @JsonProperty("conversation_id")
    private String conversationId;

    List<String> participants;
}