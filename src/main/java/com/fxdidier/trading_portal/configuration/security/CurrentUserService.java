package com.fxdidier.trading_portal.configuration.security;

import com.fxdidier.trading_portal.app.domain.entity.User;
import com.fxdidier.trading_portal.app.service.UserService;
import com.fxdidier.trading_portal.util.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserService userService;

    private Authentication getAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user in context");
        }
        return auth;
    }

    private CustomUserDetails getPrincipal() {
        return (CustomUserDetails) getAuth().getPrincipal();
    }

    public Long getId() {
        return getPrincipal().getId();
    }

    public String getUsername() {
        return getPrincipal().getUsername();
    }

    /**
     * Puedes validar por authorities si quieres evitar una query:
     *
     * return getPrincipal().getAuthorities().stream()
     *         .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
     *
     * Pero aquí nos apoyamos en el dominio:
     */
    public boolean isAdmin() {
        User user = userService.getByIdOrThrow(getId());
        return user.getRole() == Role.ROLE_ADMIN;
    }

    public User getUserEntity() {
        return userService.getByIdOrThrow(getId());
    }
}
