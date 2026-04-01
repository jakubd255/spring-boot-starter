package com.example.springbootstarter.model.dto.request;

import com.example.springbootstarter.model.type.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRoleRequest {
    @NotNull
    @NotEmpty
    private Role role;
}
