package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.dto.ChangePasswordDto;
import com.scw.electronicgradebook.domain.dto.ResetPasswordDto;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.services.interfaces.ChangePasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChangePasswordServiceImpl implements ChangePasswordService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    public void changePassword(ChangePasswordDto dto) {
        User currentUser = securityUtils.getCurrentUser();

        if (!passwordEncoder.matches(dto.getOldPassword(), currentUser.getPassword()))
            throw new IllegalArgumentException("Passwords do not match");

        if (!dto.getNewPassword().equals(dto.getNewPasswordConfirm()))
            throw new IllegalArgumentException("Password and confirmed passwords do not match");

        currentUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.update(currentUser);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordDto dto) {
        User currentUser = securityUtils.getCurrentUser();

        if (!dto.getNewPassword().equals(dto.getNewPasswordConfirm()))
            throw new IllegalArgumentException("Password and confirmed passwords do not match");

        if (!passwordEncoder.matches(
                dto.getSecurityAnswer(), currentUser.getSecurityAnswer())) {
            log.warn("Incorrect security answer received for user with id "
                    + currentUser.getId());
            throw new IllegalArgumentException("Incorrect security answer");
        }

        currentUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.update(currentUser);
    }

//    @Override
//    @Transactional
//    public boolean checkSecurityAnswer(String securityAnswer) {
//        User currentUser = securityUtils.getCurrentUser();
//
//        boolean answerCorrect = passwordEncoder.matches(
//                securityAnswer, currentUser.getSecurityAnswer());
//        if (!answerCorrect)
//            log.warn("Incorrect security answer received for user with id "
//                    + currentUser.getId());
//
//        return answerCorrect;
//    }
}
