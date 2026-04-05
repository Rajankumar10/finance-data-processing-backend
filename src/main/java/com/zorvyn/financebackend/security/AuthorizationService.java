package com.zorvyn.financebackend.security;

import com.zorvyn.financebackend.enums.Role;
import com.zorvyn.financebackend.exception.ApiException;
import com.zorvyn.financebackend.model.AppUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    // Checks whether the current logged-in user has one of the allowed roles.
    public void requireRole(Role... allowedRoles) {
        AppUser currentUser = RequestContext.getCurrentUser();
        if (currentUser == null) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Authentication required.");
        }

        for (Role role : allowedRoles) {
            if (currentUser.getRole() == role) {
                return;
            }
        }

        throw new ApiException(HttpStatus.FORBIDDEN, "You do not have permission to do this action.");
    }
}
