package com.example.springbootstarter.repository.query.request;

import com.example.springbootstarter.repository.query.specification.UserSpecification;
import com.example.springbootstarter.model.type.Role;
import com.example.springbootstarter.model.entity.User;
import com.example.springbootstarter.repository.query.filter.UserFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQuery {
    //Pagination and sorting
    private int page = 0;
    private int pageSize = 10;
    private String sortBy = "id";
    private String sortDir = "asc";

    //Filters and searching
    private String search;
    private List<Role> roles;
    private Boolean verified;
    private Boolean active;

    public Pageable toPageable() {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return PageRequest.of(page, pageSize, sort);
    }

    public UserFilter toFilter() {
        return new UserFilter(search, roles, verified, active);
    }

    public Specification<User> toSpecification() {
        return UserSpecification.withFilters(this.toFilter());
    }
}
