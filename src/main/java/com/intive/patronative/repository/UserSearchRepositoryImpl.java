package com.intive.patronative.repository;

import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.dto.profile.UserStatus;
import com.intive.patronative.repository.model.Role;
import com.intive.patronative.repository.model.Status;
import com.intive.patronative.repository.model.TechnologyGroup;
import com.intive.patronative.repository.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.SPACE;

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

        final List<Predicate> predicates = buildPredicates(userSearchDTO, criteriaBuilder, root);
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));

        final var typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    private List<Predicate> buildPredicates(final UserSearchDTO userSearchDTO, final CriteriaBuilder criteriaBuilder,
                                            final Root<User> root) {
        final List<Predicate> predicates = new ArrayList<>();
        if (userSearchDTO.getRole() != null) {
            predicates.add(createRoleByNamePredicate(userSearchDTO.getRole(), criteriaBuilder, root));
        }
        if (userSearchDTO.getStatus() != null) {
            predicates.add(createStatusByNamePredicate(userSearchDTO.getStatus(), criteriaBuilder, root));
        }
        if (userSearchDTO.getTechnologyGroup() != null) {
            predicates.add(createTechnologyGroupByNamePredicate(userSearchDTO.getTechnologyGroup(), criteriaBuilder, root));
        }
        if (userSearchDTO.getOther() != null) {
            predicates.add(createOtherPredicate(userSearchDTO.getOther(), criteriaBuilder, root));
            return predicates;
        }
        if (userSearchDTO.getFirstName() != null) {
            predicates.add(createLikePredicate(userSearchDTO.getFirstName(), root.get("firstName"), criteriaBuilder));
        }
        if (userSearchDTO.getLastName() != null) {
            predicates.add(createLikePredicate(userSearchDTO.getLastName(), root.get("lastName"), criteriaBuilder));
        }
        if (userSearchDTO.getLogin() != null) {
            predicates.add(createLikePredicate(userSearchDTO.getLogin(), root.get("login"), criteriaBuilder));
        }

        return predicates;
    }


    private Predicate createRoleByNamePredicate(final UserRole role, final CriteriaBuilder criteriaBuilder,
                                                final Root<User> root) {
        final Join<User, Role> roleJoin = root.join("role", JoinType.LEFT);
        return criteriaBuilder.equal(roleJoin.get("name"), role);
    }

    private Predicate createStatusByNamePredicate(final UserStatus status, final CriteriaBuilder criteriaBuilder,
                                                  final Root<User> root) {
        final Join<User, Status> statusJoin = root.join("status", JoinType.LEFT);
        return criteriaBuilder.equal(statusJoin.get("name"), status);
    }

    private Predicate createTechnologyGroupByNamePredicate(final String technologyGroup,
                                                           final CriteriaBuilder criteriaBuilder, final Root<User> root) {
        final Join<User, TechnologyGroup> technologyGroupsJoin = root.join("technologyGroups", JoinType.LEFT);
        return criteriaBuilder.like(
                criteriaBuilder.lower(
                        technologyGroupsJoin.get("name")
                ),
                technologyGroup.toLowerCase()
        );
    }

    private Predicate createOtherPredicate(final String other, final CriteriaBuilder criteriaBuilder,
                                           final Root<User> root) {
        final boolean hasSpace = other.contains(SPACE);
        return hasSpace
                ? createOtherPredicateComplexCase(other, criteriaBuilder, root)
                : createOtherPredicateOneWordCase(other, criteriaBuilder, root);
    }

    private Predicate createOtherPredicateComplexCase(final String other, final CriteriaBuilder criteriaBuilder,
                                                      final Root<User> root) {
        return criteriaBuilder.or(
                createOtherPredicateOneWordCase(other, criteriaBuilder, root),
                createOtherPredicateWithSpaceCase(other, criteriaBuilder, root)
        );
    }

    private Predicate createOtherPredicateOneWordCase(final String other, final CriteriaBuilder criteriaBuilder,
                                                      final Root<User> root) {
        return criteriaBuilder.or(
                createLikePredicate(other, root.get("login"), criteriaBuilder),
                createLikePredicate(other, root.get("firstName"), criteriaBuilder),
                createLikePredicate(other, root.get("lastName"), criteriaBuilder)
        );
    }

    private Predicate createOtherPredicateWithSpaceCase(final String other, final CriteriaBuilder criteriaBuilder,
                                                        final Root<User> root) {
        return criteriaBuilder.or(
                createFirstAndLastNamePredicate(other, criteriaBuilder, root, false),
                createFirstAndLastNamePredicate(other, criteriaBuilder, root, true)
        );
    }

    private Predicate createFirstAndLastNamePredicate(final String other, final CriteriaBuilder criteriaBuilder,
                                                      final Root<User> root, final boolean isReversed) {
        final var firstField = isReversed ? "lastName" : "firstName";
        final var secondField = isReversed ? "firstName" : "lastName";
        final Expression<String> concatenatedFieldSpaceFieldExpression =
                createConcatenatedFieldSpaceFieldExpression(criteriaBuilder, root, firstField, secondField);
        return createLikePredicate(other, concatenatedFieldSpaceFieldExpression, criteriaBuilder);
    }

    private Expression<String> createConcatenatedFieldSpaceFieldExpression(final CriteriaBuilder criteriaBuilder,
                                                                           final Root<User> root,
                                                                           final String firstField,
                                                                           final String secondField) {
        return criteriaBuilder.concat(
                criteriaBuilder.concat(
                        root.get(firstField),
                        SPACE
                ),
                root.get(secondField)
        );
    }

    private Predicate createLikePredicate(final String value, final Expression<String> expression,
                                          final CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(
                        expression
                ),
                "%" + value.toLowerCase() + "%"
        );
    }
}