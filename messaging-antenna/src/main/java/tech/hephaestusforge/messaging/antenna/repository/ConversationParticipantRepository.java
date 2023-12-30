package tech.hephaestusforge.messaging.antenna.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import tech.hephaestusforge.messaging.antenna.model.datasource.ConversationParticipantModel;

import java.util.List;

@Repository
public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipantModel, String> {
    @Modifying
    @Transactional
    List<ConversationParticipantModel> findConversationParticipantsByConversationId(String conversationId);
}
