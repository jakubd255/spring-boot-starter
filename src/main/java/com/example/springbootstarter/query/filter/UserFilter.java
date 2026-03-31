package com.example.springbootstarter.query.filter;

import com.example.springbootstarter.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFilter {
    private String search;
    private List<Role> roles;
    private Boolean verified;
    private Boolean active;
}
