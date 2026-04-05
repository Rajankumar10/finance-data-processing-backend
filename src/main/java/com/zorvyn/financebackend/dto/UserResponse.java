package com.zorvyn.financebackend.dto;

import com.zorvyn.financebackend.enums.Role;
import com.zorvyn.financebackend.enums.UserStatus;

public record UserResponse(
        Long id,
        String name,
        String email,
        Role role,
        UserStatus status
) {
}
