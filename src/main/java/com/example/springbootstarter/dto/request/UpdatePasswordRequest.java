package com.example.springbootstarter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePasswordRequest {
    @NotNull
    @NotEmpty
    @Length(min = 8)
    private String currentPassword;

    @NotNull
    @NotEmpty
    @Length(min = 8)
    private String newPassword;
}
