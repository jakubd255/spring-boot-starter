package com.example.springbootstarter.repository.query.specification;

import com.example.springbootstarter.model.entity.User;
import com.example.springbootstarter.repository.query.filter.UserFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<User> withFilters(UserFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filter.getSearch() != null && !filter.getSearch().isBlank()) {
                String like = "%" + filter.getSearch().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("email")), like),
                        cb.like(cb.lower(root.get("fullName")), like)
                ));
            }

            if(filter.getRoles() != null && !filter.getRoles().isEmpty()) {
                predicates.add(root.get("role").in(filter.getRoles()));
            }

            if(filter.getVerified() != null) {
                predicates.add(cb.equal(root.get("verified"), filter.getVerified()));
            }

            if(filter.getActive() != null) {
                predicates.add(cb.equal(root.get("active"), filter.getActive()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
