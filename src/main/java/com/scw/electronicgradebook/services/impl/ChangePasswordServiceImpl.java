package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.dto.ChangePasswordDto;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.services.ChangePasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangePasswordServiceImpl implements ChangePasswordService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void changePassword(ChangePasswordDto dto) {
        User currentUser = getCurrentUser();

        if (!passwordEncoder.matches(dto.getOldPassword(), currentUser.getPassword()))
            throw new IllegalArgumentException("Passwords do not match");

        if (!dto.getNewPassword().equals(dto.getNewPasswordConfirm()))
            throw new IllegalArgumentException("Password and confirmed passwords do not match");

        currentUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.update(currentUser);
    }

    private User getCurrentUser() {
        String currentLogin = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return userRepository.findByLogin(currentLogin).orElseThrow();
    }
}
