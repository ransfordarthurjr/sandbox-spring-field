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
public class AntennaCreateConversationRequestModel {
    @JsonProperty("group_conversation")
    @SerializedName("group_conversation")
    private String groupConversation;

    List<String> participants;
}