package tech.hephaestusforge.messaging.antenna.model.datasource;

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
public class ConversationModel {
    @Id
    private String id;

    private LocalDateTime timestamp;
}
