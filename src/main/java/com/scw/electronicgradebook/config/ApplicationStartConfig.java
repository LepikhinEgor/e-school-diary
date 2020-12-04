package com.scw.electronicgradebook.config;

import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.dto.RegistrationDto;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApplicationStartConfig {

    @Value("${admin.login}")
    private String adminLogin;

    @Value("${admin.password}")
    private String adminPassword;

    private final UserRepository userRepository;

    private final UserService userService;

    @Transactional
    @EventListener(classes = ApplicationReadyEvent.class)
    public void createAdmin() {
        Optional<User> foundAdmin = userRepository.findByLogin("admin");

        if (foundAdmin.isEmpty()) {
            userService.register(buildAdminData());
        }
    }

    private RegistrationDto buildAdminData() {
        RegistrationDto dto = new RegistrationDto();
        dto.setLogin(adminLogin);
        dto.setPassword(adminPassword);
        dto.setPasswordConfirm(adminPassword);
        dto.setName("Admin");
        dto.setUserType("admin");

        return dto;
    }
}
