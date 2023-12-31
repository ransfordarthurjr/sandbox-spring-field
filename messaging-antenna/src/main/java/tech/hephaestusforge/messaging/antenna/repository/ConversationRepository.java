package tech.hephaestusforge.messaging.antenna.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.springframework.stereotype.Repository;
import tech.hephaestusforge.messaging.antenna.configuration.persistence.ApplicationPersistenceUnit;
import tech.hephaestusforge.messaging.antenna.model.antennae.message.AntennaCreateConversationParticipantsRequestModel;
import tech.hephaestusforge.messaging.antenna.model.antennae.message.AntennaCreateConversationParticipantsResponseModel;
import tech.hephaestusforge.messaging.antenna.model.antennae.message.AntennaCreateConversationRequestModel;
import tech.hephaestusforge.messaging.antenna.model.antennae.message.AntennaCreateConversationResponseModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.ConversationModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.ConversationParticipantModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.derived.ConversationJoinParticipantModel;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ConversationRepository {
    private final ApplicationPersistenceUnit persistenceUnit;

    private PersistenceUnitInfoDescriptor persistenceUnitInfoDescriptor;

    private void initPersistenceUnitInfoDescriptor() {
        if (this.persistenceUnitInfoDescriptor == null)
            this.persistenceUnitInfoDescriptor = new PersistenceUnitInfoDescriptor(persistenceUnit);
    }

    public Optional<ConversationModel> fetchConversation(String conversationId) {
        this.initPersistenceUnitInfoDescriptor();

        try (EntityManager entityManager = new EntityManagerFactoryBuilderImpl(persistenceUnitInfoDescriptor, new HashMap<>())
                .build().createEntityManager()) {

            String sql = "FROM ConversationModel";
            sql += " WHERE id = :id";

            return Optional.ofNullable(entityManager.createQuery(sql, ConversationModel.class)
                    .setParameter("id", conversationId)
                    .setMaxResults(1)
                    .getResultList().getFirst());

        } catch (Exception ex) {
            log.error("Exception:::\n{}", ExceptionUtils.getStackTrace(ex));
            return Optional.empty();
        }
    }

    public List<ConversationParticipantModel> findConversationParticipantsByConversationId(String conversationId) {
        this.initPersistenceUnitInfoDescriptor();

        try (EntityManager entityManager = new EntityManagerFactoryBuilderImpl(persistenceUnitInfoDescriptor, new HashMap<>())
                .build().createEntityManager()) {

            String sql = "FROM ConversationParticipantModel";
            sql += " WHERE conversationId = :conversationId";

            return entityManager.createQuery(sql, ConversationParticipantModel.class)
                    .setParameter("conversationId", conversationId)
                    .getResultList();

        } catch (Exception ex) {
            log.error("Exception:::\n{}", ExceptionUtils.getStackTrace(ex));
            return new ArrayList<>();
        }
    }

    public List<ConversationJoinParticipantModel> findConversation(String groupConversation, List<String> participants) {
        this.initPersistenceUnitInfoDescriptor();

        try (EntityManager entityManager = new EntityManagerFactoryBuilderImpl(persistenceUnitInfoDescriptor, new HashMap<>())
                .build().createEntityManager()) {

            String sql = "SELECT c.id, c.timestamp, c.groupConversation, p.id as participant_id, p.participant";
            sql += " FROM ConversationModel c LEFT JOIN ConversationParticipantModel p ON c.id = p.conversationId";
            sql += " WHERE c.groupConversation = :group_conversation AND p.participant IN :participants";

            return entityManager.createQuery(sql, ConversationJoinParticipantModel.class)
                    .setParameter("group_conversation", groupConversation)
                    .setParameter("participants", participants)
                    .getResultList();

        } catch (Exception ex) {
            log.error("Exception:::\n{}", ExceptionUtils.getStackTrace(ex));
            return null;
        }
    }

    public AntennaCreateConversationResponseModel createConversation(AntennaCreateConversationRequestModel request) {
        this.initPersistenceUnitInfoDescriptor();

        try (EntityManager entityManager = new EntityManagerFactoryBuilderImpl(persistenceUnitInfoDescriptor, new HashMap<>())
                .build().createEntityManager()) {

            var cvs = ConversationModel.builder()
                    .id(UUID.randomUUID().toString())
                    .timestamp(LocalDateTime.now())
                    .groupConversation(request.getGroupConversation())
                    .build();

            entityManager.persist(cvs);
            entityManager.flush();

            return AntennaCreateConversationResponseModel.builder()
                    .id(cvs.getId())
                    .timestamp(cvs.getTimestamp())
                    .build();
        } catch (Exception ex) {
            log.error("Exception:::\n{}", ExceptionUtils.getStackTrace(ex));
            return null;
        }
    }

    @Transactional
    public AntennaCreateConversationParticipantsResponseModel addConversationParticipants(AntennaCreateConversationParticipantsRequestModel request) {
        this.initPersistenceUnitInfoDescriptor();

        List<ConversationParticipantModel> participants = new ArrayList<>();

        try (EntityManager entityManager = new EntityManagerFactoryBuilderImpl(persistenceUnitInfoDescriptor, new HashMap<>())
                .build().createEntityManager()) {

            for (String pnt : request.getParticipants()) {
                var participant = ConversationParticipantModel.builder()
                        .conversationId(request.getConversationId())
                        .participant(pnt)
                        .build();

                entityManager.persist(participant);
                entityManager.flush();

                participants.add(participant);
            }


        } catch (Exception ex) {
            log.error("Exception:::\n{}", ExceptionUtils.getStackTrace(ex));
            return null;
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
