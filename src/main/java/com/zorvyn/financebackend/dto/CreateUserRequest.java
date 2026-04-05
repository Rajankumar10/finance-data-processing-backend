package com.zorvyn.financebackend.dto;

import com.zorvyn.financebackend.enums.Role;
import com.zorvyn.financebackend.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "name is required")
        @Size(max = 100, message = "name can have at most 100 characters")
        String name,

        @NotBlank(message = "email is required")
        @Email(message = "email must be valid")
        String email,

        @NotNull(message = "role is required")
        Role role,

        @NotNull(message = "status is required")
        UserStatus status
) {
}
