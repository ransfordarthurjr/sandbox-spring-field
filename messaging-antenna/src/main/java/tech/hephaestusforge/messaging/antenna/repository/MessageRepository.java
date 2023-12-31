/*
package tech.hephaestusforge.messaging.antenna.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.hephaestusforge.messaging.antenna.model.datasource.MessageModel;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageModel, String> {
    @Transactional
    @Query(value = "SELECT msg FROM MessageModel msg WHERE msg.conversationId = :conversationId ORDER BY msg.timestamp ASC ")
    List<MessageModel> findMessagesByConversationId(@Param("conversationId") String conversationId);
}
*/
