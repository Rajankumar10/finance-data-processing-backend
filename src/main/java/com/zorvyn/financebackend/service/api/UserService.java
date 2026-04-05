package com.zorvyn.financebackend.service.api;

import com.zorvyn.financebackend.dto.CreateUserRequest;
import com.zorvyn.financebackend.dto.UpdateUserRequest;
import com.zorvyn.financebackend.dto.UserResponse;

import java.util.List;

public interface UserService {

    // Returns all users for admin management screen.
    List<UserResponse> getAllUsers();

    // Returns one user by id.
    UserResponse getUserById(Long id);

    // Creates a new user.
    UserResponse createUser(CreateUserRequest request);

    // Updates an existing user.
    UserResponse updateUser(Long id, UpdateUserRequest request);
}
