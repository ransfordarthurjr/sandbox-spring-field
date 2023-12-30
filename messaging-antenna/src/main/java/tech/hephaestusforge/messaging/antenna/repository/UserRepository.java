package tech.hephaestusforge.messaging.antenna.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.hephaestusforge.messaging.antenna.model.datasource.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository implements UserRepositoryInterface {
    private final EntityManager entityManager;

    @Override
    public Optional<UserModel> findUserByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserModel> criteriaQuery = criteriaBuilder.createQuery(UserModel.class);

        Root<UserModel> user = criteriaQuery.from(UserModel.class);

        return entityManager
                .createQuery(criteriaQuery.where(criteriaBuilder.equal(user.get("username"), username)))
                .getResultList().stream().findFirst();
    }

    // @Transactional
    // @Query(value = "SELECT u FROM UserModel u WHERE u.username IN :usernames ORDER BY u.firstname ASC, u.lastname ASC")
    public List<UserModel> findUsersByUsernames(@Param("usernames") List<String> usernames) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<UserModel> criteriaQuery = criteriaBuilder.createQuery(UserModel.class);

        Root<UserModel> user = criteriaQuery.from(UserModel.class);

        CriteriaBuilder.In<String> matchingUsersInClause = criteriaBuilder.in(user.get("username"));
        usernames.forEach(matchingUsersInClause::value);

        return entityManager.createQuery(criteriaQuery.select(user).where(matchingUsersInClause)).getResultList();
    }

    // @Transactional
    // @Query(value = "SELECT u FROM UserModel u WHERE (u.firstname LIKE %:searchTerm% OR u.lastname LIKE %:searchTerm%) ORDER BY u.firstname ASC, u.lastname ASC")
    public List<UserModel> searchUsers(@Param("searchTerm") String searchTerm) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserModel> criteriaQuery = criteriaBuilder.createQuery(UserModel.class);

        Root<UserModel> user = criteriaQuery.from(UserModel.class);

        String[] searchTermParts = searchTerm.trim().replaceAll("\\s+", " ").trim().split(" ");
        // log.info("Parts length:::{}", searchTermParts.length);

        List<Predicate> orPredicates = new ArrayList<>();
        for (String searchTermPart : searchTermParts) {
            orPredicates.add(criteriaBuilder.like(user.get("firstname"), "%" + searchTermPart + "%"));
            orPredicates.add(criteriaBuilder.like(user.get("lastname"), "%" + searchTermPart + "%"));
        }

        List<Predicate> predicates = new ArrayList<>() {{
            add(criteriaBuilder.or(
                    orPredicates.toArray(new Predicate[0])
            ));
        }};

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
