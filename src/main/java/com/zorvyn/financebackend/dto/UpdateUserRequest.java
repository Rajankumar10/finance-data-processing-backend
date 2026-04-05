package com.zorvyn.financebackend.dto;

import com.zorvyn.financebackend.enums.Role;
import com.zorvyn.financebackend.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Size(max = 100, message = "name can have at most 100 characters")
        String name,

        @Email(message = "email must be valid")
        String email,

        Role role,
        UserStatus status
) {
}
