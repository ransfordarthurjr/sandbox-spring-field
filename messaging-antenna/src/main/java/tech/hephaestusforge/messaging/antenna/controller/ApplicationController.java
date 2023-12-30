package tech.hephaestusforge.messaging.antenna.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.hephaestusforge.messaging.antenna.model.antennae.message.*;
import tech.hephaestusforge.messaging.antenna.model.antennae.user.AntennaCreateUserRequestModel;
import tech.hephaestusforge.messaging.antenna.model.antennae.user.AntennaUsersRequestModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.ConversationModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.ConversationParticipantModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.MessageModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.UserModel;
import tech.hephaestusforge.messaging.antenna.service.ApplicationService;
import tech.hephaestusforge.messaging.antenna.util.TypeAdapterUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/antennae")
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {
    private final Gson gson = new GsonBuilder().disableHtmlEscaping()
            .registerTypeAdapter(LocalDateTime.class, new TypeAdapterUtil.LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new TypeAdapterUtil.LocalDateAdapter())
            .create();
    private final ApplicationService service;

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> fetchUser(@RequestParam("username") String username) {
        log.info("\nIncoming User:::username:{}", username);

        UserModel response = this.service.fetchUser(username);
        log.info("\nOutgoing UserModel:::\n{}\n", this.gson.toJson(response));

        return ResponseEntity
                .status(response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> createUser(@RequestBody AntennaCreateUserRequestModel request) {
        log.info("\nIncoming AntennaCreateUserRequestModel:::\n{}\n", this.gson.toJson(request));

        UserModel response = this.service.createUser(request);
        log.info("\nOutgoing UserModel:::\n{}\n", this.gson.toJson(response));

        return ResponseEntity
                .status(response != null ? HttpStatus.CREATED : HttpStatus.EXPECTATION_FAILED)
                .body(response);
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserModel>> searchUsers(@RequestParam("search") String searchTerm) {
        log.info("\nIncoming User Search:::{}", searchTerm);

        List<UserModel> response = this.service.searchUsers(searchTerm);
        log.info("\nOutgoing List<UserModel>:::\n{}\n", this.gson.toJson(response));

        return ResponseEntity
                .status(response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserModel>> fetchUsers(@RequestBody AntennaUsersRequestModel request) {
        log.info("\nIncoming AntennaUsersRequestModel:::\n{}\n", this.gson.toJson(request));

        List<UserModel> response = this.service.fetchUsers(request);
        log.info("\nOutgoing List<UserModel>:::\n{}\n", this.gson.toJson(response));

        return ResponseEntity
                .status(response != null ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED)
                .body(response);
    }

    @GetMapping(value = "/conversation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConversationModel> fetchConversation(@RequestParam("id") String conversationId) {
        log.info("\nIncoming Conversation:::Id:{}", conversationId);

        ConversationModel response = this.service.fetchConversation(conversationId);
        log.info("\nOutgoing ConversationModel:::\n{}\n", this.gson.toJson(response));

        return ResponseEntity
                .status(response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    @PostMapping(value = "/conversation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AntennaCreateConversationResponseModel> createConversation(@RequestBody AntennaCreateConversationRequestModel request) {
        log.info("\nIncoming AntennaCreateConversationRequestModel:::\n{}\n", this.gson.toJson(request));

        AntennaCreateConversationResponseModel response = this.service.createConversation(request);
        log.info("\nOutgoing AntennaCreateConversationResponseModel:::\n{}\n", this.gson.toJson(response));

        return ResponseEntity
                .status(response != null ? HttpStatus.CREATED : HttpStatus.EXPECTATION_FAILED)
                .body(response);
    }

    @GetMapping(value = "/conversation/participants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConversationParticipantModel>> fetchConversationParticipants(@RequestParam("conversation_id") String conversationId) {
        log.info("\nIncoming Conversation:Participant:::Id:{}", conversationId);

        List<ConversationParticipantModel> response = this.service.fetchConversationParticipants(conversationId);
        log.info("\nOutgoing <List<ConversationParticipantModel>:::\n{}\n", this.gson.toJson(response));

        return ResponseEntity
                .status(response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    @PostMapping(value = "/conversation/participants", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AntennaCreateConversationParticipantsResponseModel> addConversationParticipants(@RequestBody AntennaCreateConversationParticipantsRequestModel request) {
        log.info("\nIncoming AntennaCreateConversationParticipantsRequestModel:::\n{}\n", this.gson.toJson(request));

        AntennaCreateConversationParticipantsResponseModel response = this.service.addConversationParticipants(request);
        log.info("\nOutgoing AntennaCreateConversationParticipantsResponseModel:::\n{}\n", this.gson.toJson(response));

        return ResponseEntity
                .status(response != null ? HttpStatus.CREATED : HttpStatus.EXPECTATION_FAILED)
                .body(response);
    }

    @GetMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MessageModel>> fetchMessagesByConversation(@RequestParam("conversation_id") String conversationId) {
        log.info("\nIncoming Messages:::Id:{}", conversationId);

        List<MessageModel> response = this.service.fetchMessagesByConversation(conversationId);
        log.info("\nOutgoing List<MessageModel>:::\n{}\n", this.gson.toJson(response));

        return ResponseEntity
                .status(response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    @PostMapping(value = "/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AntennaCreateMessageResponseModel> createMessage(@RequestBody AntennaCreateMessageRequestModel request) {
        log.info("\nIncoming AntennaCreateMessageRequestModel:::\n{}\n", this.gson.toJson(request));

        AntennaCreateMessageResponseModel response = this.service.createMessage(request);
        log.info("\nOutgoing AntennaCreateMessageResponseModel:::\n{}\n", this.gson.toJson(response));

        return ResponseEntity
                .status(response != null ? HttpStatus.CREATED : HttpStatus.EXPECTATION_FAILED)
                .body(response);
    }
}