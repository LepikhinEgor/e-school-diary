package com.scw.electronicgradebook.services;

import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.entities.Role;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.domain.enums.SecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserRepository userRepository;

    private final HttpServletRequest httpServletRequest;

    @Transactional
    public User getCurrentUser() {
        String currentLogin = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return userRepository.findByLogin(currentLogin).orElseThrow();
    }

    public boolean hasRole(User user, SecurityRole securityRole) {
        if (user.getRoles() == null || securityRole == null)
            throw new IllegalArgumentException();

        for (Role role : user.getRoles()) {
            if (role.getName().equals(securityRole.name()))
                return true;
        }

        return false;
    }

    public String getClientIP() {
        String xfHeader = httpServletRequest.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return httpServletRequest.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    public String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
