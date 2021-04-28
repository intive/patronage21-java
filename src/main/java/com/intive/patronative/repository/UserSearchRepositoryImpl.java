package com.intive.patronative.repository;

import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.repository.model.Role;
import com.intive.patronative.repository.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Repository
public class UserSearchRepositoryImpl implements UserSearchRepository {

    private final EntityManager entityManager;

    @Override
    public List<User> findAllUsers(final UserSearchDTO userSearchDTO) {
        
        if (userSearchDTO == null) {
            return Collections.emptyList();
        }
        final var criteriaBuilder = entityManager.getCriteriaBuilder();
        final var criteriaQuery = criteriaBuilder.createQuery(User.class);
        final var root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        final List<Predicate> predicates = addPredicates(userSearchDTO, criteriaBuilder, root);
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));

        final var typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    private List<Predicate> addPredicates(final UserSearchDTO userSearchDTO, final CriteriaBuilder criteriaBuilder,
                                          final Root<User> root) {
        final List<Predicate> predicates = new ArrayList<>();
        if (userSearchDTO.getFirstName() != null) {
            predicates.add(createLikePredicate(userSearchDTO.getFirstName(), "firstName", criteriaBuilder, root));
        }
        if (userSearchDTO.getLastName() != null) {
            predicates.add(createLikePredicate(userSearchDTO.getLastName(), "lastName", criteriaBuilder, root));
        }
        if (userSearchDTO.getLogin() != null) {
            predicates.add(createLikePredicate(userSearchDTO.getLogin(), "login", criteriaBuilder, root));
        }
        if (userSearchDTO.getRole() != null) {
            predicates.add(createRoleByNamePredicate(userSearchDTO.getRole().toString(), criteriaBuilder, root));
        }

        return predicates;
    }

    private Predicate createRoleByNamePredicate(final String role, final CriteriaBuilder criteriaBuilder,
                                                final Root<User> root) {
        final Join<User, Role> roleJoin = root.join("role", JoinType.LEFT);
        return criteriaBuilder.equal(roleJoin.get("name"), role);
    }

    private Predicate createLikePredicate(final String value, final String fieldName,
                                          final CriteriaBuilder criteriaBuilder, final Root<User> root) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(
                        root.get(fieldName)
                ),
                value.toLowerCase()
        );
    }
}