/*
package tech.hephaestusforge.messaging.antenna.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.hephaestusforge.messaging.antenna.model.antennae.message.AntennaCreateMessageRequestModel;
import tech.hephaestusforge.messaging.antenna.model.antennae.message.AntennaCreateMessageResponseModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.MessageModel;
import tech.hephaestusforge.messaging.antenna.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository repository;

    public List<MessageModel> fetchMessagesByConversation(String conversationId) {
        return this.repository.findMessagesByConversationId(conversationId);
    }

    public AntennaCreateMessageResponseModel createMessage(AntennaCreateMessageRequestModel request) {
        var msg = MessageModel.builder()
                .conversationId(request.getConversationId())
                .timestamp(LocalDateTime.now())
                .sender(request.getSender())
                .message(request.getMessage())
                .opened("0")
                .build();

        MessageModel message = this.repository.save(msg);

        return AntennaCreateMessageResponseModel.builder()
                .timestamp(message.getTimestamp())
                .conversationId(message.getConversationId())
                .messageId(message.getId())
                .build();
    }
}
*/
