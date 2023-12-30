package tech.hephaestusforge.messaging.antenna.model.datasource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "ant_conversations_participants")
public class ConversationParticipantModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "conversation_id")
    @JsonProperty("conversation_id")
    @SerializedName("conversation_id")
    private String conversationId;

    private String participant;
}
