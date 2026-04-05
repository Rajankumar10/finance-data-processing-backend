package com.zorvyn.financebackend.service.impl;

import com.zorvyn.financebackend.dto.CreateUserRequest;
import com.zorvyn.financebackend.dto.UpdateUserRequest;
import com.zorvyn.financebackend.dto.UserResponse;
import com.zorvyn.financebackend.exception.ApiException;
import com.zorvyn.financebackend.mapper.FinanceMapper;
import com.zorvyn.financebackend.model.AppUser;
import com.zorvyn.financebackend.repository.AppUserRepository;
import com.zorvyn.financebackend.service.api.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final AppUserRepository appUserRepository;

    public UserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return appUserRepository.findAll().stream()
                .map(FinanceMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return FinanceMapper.toUserResponse(getUser(id));
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        String email = request.email().trim().toLowerCase();
        if (appUserRepository.existsByEmailIgnoreCase(email)) {
            throw new ApiException(HttpStatus.CONFLICT, "User email already exists.");
        }

        AppUser user = new AppUser();
        user.setName(request.name().trim());
        user.setEmail(email);
        user.setRole(request.role());
        user.setStatus(request.status());
        return FinanceMapper.toUserResponse(appUserRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        AppUser user = getUser(id);

        if (request.name() != null) {
            user.setName(request.name().trim());
        }
        if (request.email() != null) {
            String email = request.email().trim().toLowerCase();
            appUserRepository.findByEmailIgnoreCase(email)
                    .filter(existing -> !existing.getId().equals(id))
                    .ifPresent(existing -> {
                        throw new ApiException(HttpStatus.CONFLICT, "User email already exists.");
                    });
            user.setEmail(email);
        }
        if (request.role() != null) {
            user.setRole(request.role());
        }
        if (request.status() != null) {
            user.setStatus(request.status());
        }

        return FinanceMapper.toUserResponse(appUserRepository.save(user));
    }

    private AppUser getUser(Long id) {
        return appUserRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found."));
    }
}
