package tech.hephaestusforge.messaging.antenna.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.hephaestusforge.messaging.antenna.model.antennae.message.*;
import tech.hephaestusforge.messaging.antenna.model.antennae.user.AntennaCreateUserRequestModel;
import tech.hephaestusforge.messaging.antenna.model.antennae.user.AntennaUsersRequestModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.ConversationModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.ConversationParticipantModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.MessageModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.UserModel;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {
    private final ConversationService conversation;
    private final MessageService message;
    private final UserService user;

    public UserModel fetchUser(String username) {
        return this.user.fetchUser(username);
    }

    public UserModel createUser(AntennaCreateUserRequestModel request) {
        return this.user.createUser(request);
    }

    public List<UserModel> fetchUsers(AntennaUsersRequestModel request) {
        return this.user.fetchUsers(request);
    }

    public List<UserModel> searchUsers(String searchTerm) {
        return this.user.searchUsers(searchTerm);
    }

    public ConversationModel fetchConversation(String conversationId) {
        return this.conversation.fetchConversation(conversationId);
    }

    public AntennaCreateConversationResponseModel createConversation(AntennaCreateConversationRequestModel request) {
        return this.conversation.createConversation(request);
    }

    public List<ConversationParticipantModel> fetchConversationParticipants(String conversationId) {
        return this.conversation.fetchConversationParticipants(conversationId);
    }

    public AntennaCreateConversationParticipantsResponseModel addConversationParticipants(AntennaCreateConversationParticipantsRequestModel request) {
        return this.conversation.addConversationParticipants(request);
    }

    public List<MessageModel> fetchMessagesByConversation(String conversationId) {
        return this.message.fetchMessagesByConversation(conversationId);
    }

    public AntennaCreateMessageResponseModel createMessage(AntennaCreateMessageRequestModel request) {
        return this.message.createMessage(request);
    }
}
