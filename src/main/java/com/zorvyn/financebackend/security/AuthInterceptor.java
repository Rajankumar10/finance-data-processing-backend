package com.zorvyn.financebackend.security;

import com.zorvyn.financebackend.constants.HeaderConstants;
import com.zorvyn.financebackend.enums.UserStatus;
import com.zorvyn.financebackend.exception.ApiException;
import com.zorvyn.financebackend.model.AppUser;
import com.zorvyn.financebackend.repository.AppUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final AppUserRepository appUserRepository;

    public AuthInterceptor(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Health and H2 console are kept open for local development.
        String path = request.getRequestURI();
        if (path.equals("/health") || path.startsWith("/h2-console") || path.startsWith("/error")) {
            return true;
        }

        String userIdHeader = request.getHeader(HeaderConstants.USER_ID);
        if (userIdHeader == null || userIdHeader.isBlank()) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Missing X-User-Id header.");
        }

        Long userId;
        try {
            userId = Long.valueOf(userIdHeader);
        } catch (NumberFormatException exception) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "X-User-Id must be a number.");
        }

        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "User not found for the given X-User-Id."));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Inactive users cannot access this API.");
        }

        RequestContext.setCurrentUser(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RequestContext.clear();
    }
}
