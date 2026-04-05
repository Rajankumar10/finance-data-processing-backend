package com.zorvyn.financebackend.controller.api;

import com.zorvyn.financebackend.dto.CreateUserRequest;
import com.zorvyn.financebackend.dto.UpdateUserRequest;
import com.zorvyn.financebackend.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/api/users")
public interface UserControllerApi {

    // Used by admin to see all users in the system.
    @GetMapping
    List<UserResponse> getUsers();

    // Used by admin to see one user by id.
    @GetMapping("/{id}")
    UserResponse getUser(@PathVariable Long id);

    // Used by admin to create a new user with role and status.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserResponse createUser(@Valid @RequestBody CreateUserRequest request);

    // Used by admin to update user details, role, or status.
    @PatchMapping("/{id}")
    UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request);
}
