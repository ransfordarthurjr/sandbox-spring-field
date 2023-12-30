package tech.hephaestusforge.messaging.antenna.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.hephaestusforge.messaging.antenna.model.datasource.ConversationModel;

import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationModel, String> {
    Optional<ConversationModel> findConversationById(String id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO conversations (id, timestamp)  VALUES (:#{#conversation.id}, :#{#conversation.timestamp})", nativeQuery = true)
    void createConversation(@Param("conversation") ConversationModel conversation);
}
