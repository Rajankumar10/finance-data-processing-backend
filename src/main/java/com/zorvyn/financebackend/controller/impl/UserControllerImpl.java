package com.zorvyn.financebackend.controller.impl;

import com.zorvyn.financebackend.controller.api.UserControllerApi;
import com.zorvyn.financebackend.dto.CreateUserRequest;
import com.zorvyn.financebackend.dto.UpdateUserRequest;
import com.zorvyn.financebackend.dto.UserResponse;
import com.zorvyn.financebackend.enums.Role;
import com.zorvyn.financebackend.security.AuthorizationService;
import com.zorvyn.financebackend.service.api.UserService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControllerImpl implements UserControllerApi {
    private final UserService userService;
    private final AuthorizationService authorizationService;

    public UserControllerImpl(UserService userService, AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @Override
    public List<UserResponse> getUsers() {
        // Only admin can manage users.
        authorizationService.requireRole(Role.ADMIN);
        return userService.getAllUsers();
    }

    @Override
    public UserResponse getUser(Long id) {
        authorizationService.requireRole(Role.ADMIN);
        return userService.getUserById(id);
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        authorizationService.requireRole(Role.ADMIN);
        return userService.createUser(request);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        authorizationService.requireRole(Role.ADMIN);
        return userService.updateUser(id, request);
    }
}
