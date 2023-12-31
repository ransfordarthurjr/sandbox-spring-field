package tech.hephaestusforge.messaging.antenna.model.datasource.derived;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "ant_conversations")
public class ConversationJoinParticipantModel {
    @Id
    private String id;
    private LocalDateTime timestamp;

    @Column(name = "group_conversation")
    @JsonProperty("group_conversation")
    @SerializedName("group_conversation")
    private String groupConversation;

    @Column(name = "participant_id")
    @JsonProperty("participant_id")
    @SerializedName("participant_id")
    private String participantId;

    private String participant;
}
