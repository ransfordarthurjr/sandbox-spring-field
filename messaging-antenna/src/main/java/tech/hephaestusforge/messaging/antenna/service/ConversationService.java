package tech.hephaestusforge.messaging.antenna.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.hephaestusforge.messaging.antenna.model.antennae.message.AntennaCreateConversationParticipantsRequestModel;
import tech.hephaestusforge.messaging.antenna.model.antennae.message.AntennaCreateConversationParticipantsResponseModel;
import tech.hephaestusforge.messaging.antenna.model.antennae.message.AntennaCreateConversationRequestModel;
import tech.hephaestusforge.messaging.antenna.model.antennae.message.AntennaCreateConversationResponseModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.ConversationModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.ConversationParticipantModel;
import tech.hephaestusforge.messaging.antenna.repository.ConversationParticipantRepository;
import tech.hephaestusforge.messaging.antenna.repository.ConversationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationService {
    private final ConversationRepository repository;
    private final ConversationParticipantRepository participantRepository;

    public ConversationModel fetchConversation(String conversationId) {
        return this.repository.findConversationById(conversationId).orElse(null);
    }

    public AntennaCreateConversationResponseModel createConversation(AntennaCreateConversationRequestModel request) {
        var cvs = ConversationModel.builder()
                .id(request.getConversationId() != null ? request.getConversationId() : UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .build();

        ConversationModel conversation = this.repository.save(cvs);
        this.addConversationParticipants(AntennaCreateConversationParticipantsRequestModel.builder()
                .conversationId(request.getConversationId())
                .participants(request.getParticipants())
                .build());

        return AntennaCreateConversationResponseModel.builder()
                .conversationId(conversation.getId())
                .timestamp(conversation.getTimestamp())
                .build();
    }

    public List<ConversationParticipantModel> fetchConversationParticipants(String conversationId) {
        return this.participantRepository.findConversationParticipantsByConversationId(conversationId);
    }

    public AntennaCreateConversationParticipantsResponseModel addConversationParticipants(AntennaCreateConversationParticipantsRequestModel request) {
        List<ConversationParticipantModel> participants = new ArrayList<>();

        for (String pnt : request.getParticipants()) {
            var participant = ConversationParticipantModel.builder()
                    .conversationId(request.getConversationId())
                    .participant(pnt)
                    .build();
            participants.add(this.participantRepository.save(participant));
        }

        return AntennaCreateConversationParticipantsResponseModel.builder()
                .conversationId(request.getConversationId())
                .participants(participants.stream()
                        .filter(Objects::nonNull)
                        .map(ConversationParticipantModel::getParticipant)
                        .collect(Collectors.toList()))
                .build();
    }
}
