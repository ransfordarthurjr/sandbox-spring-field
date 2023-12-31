package tech.hephaestusforge.messaging.antenna.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.hephaestusforge.messaging.antenna.model.antennae.message.*;
import tech.hephaestusforge.messaging.antenna.model.datasource.ConversationModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.ConversationParticipantModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.derived.ConversationJoinParticipantModel;
import tech.hephaestusforge.messaging.antenna.repository.ConversationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationService {
    private final ConversationRepository repository;

    public ConversationModel fetchConversation(String conversationId) {
        return this.repository.fetchConversation(conversationId).orElse(null);
    }

    public List<ConversationParticipantModel> fetchConversationParticipants(String conversationId) {
        return this.repository.findConversationParticipantsByConversationId(conversationId);
    }

    public AntennaCreateConversationResponseModel setupConversation(AntennaSetupConversationRequestModel request) {
        List<ConversationJoinParticipantModel> conversationByParticipants = this.repository
                .findConversation(request.getGroupConversation(), request.getParticipants());

        // conversation already exists
        if (conversationByParticipants != null && !conversationByParticipants.isEmpty())
            return AntennaCreateConversationResponseModel.builder()
                    .id(conversationByParticipants.getFirst().getId())
                    .timestamp(conversationByParticipants.getFirst().getTimestamp())
                    .groupConversation(conversationByParticipants.getFirst().getGroupConversation())
                    .build();

        // if not create a new conversation (with participants)
        return this.createConversation(
                AntennaCreateConversationRequestModel.builder()
                        .groupConversation(request.getGroupConversation())
                        .participants(request.getParticipants())
                        .build());
    }

    public AntennaCreateConversationResponseModel createConversation(AntennaCreateConversationRequestModel request) {
        AntennaCreateConversationResponseModel conversation = this.repository.createConversation(request);

        this.repository.addConversationParticipants(AntennaCreateConversationParticipantsRequestModel.builder()
                .conversationId(conversation.getId())
                .participants(request.getParticipants())
                .build());

        return conversation;
    }

    public AntennaCreateConversationParticipantsResponseModel addConversationParticipants(AntennaCreateConversationParticipantsRequestModel request) {
        return this.repository.addConversationParticipants(request);
    }


}
