package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.dto.RegistrationDto;
import com.scw.electronicgradebook.domain.dto.SensitiveDataDto;
import com.scw.electronicgradebook.domain.dto.UserDto;
import com.scw.electronicgradebook.domain.entities.Role;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.domain.mappers.UserMapper;
import com.scw.electronicgradebook.services.interfaces.LoginAttemptService;
import com.scw.electronicgradebook.services.interfaces.RoleService;
import com.scw.electronicgradebook.services.interfaces.SensitiveDataEncryptor;
import com.scw.electronicgradebook.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.scw.electronicgradebook.domain.enums.SecurityRole.ROLE_ADMIN;
import static java.util.Collections.singletonList;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final SensitiveDataEncryptor sensitiveDataEncryptor;

    private final RoleService roleService;

    private final SecurityUtils securityUtils;

    private final LoginAttemptService attemptService;

    @Override
    @Transactional
    public void register(RegistrationDto dto) {
        if (attemptService.isBlocked(securityUtils.getClientIP()))
            throw new LockedException("The limit of failed attempts exceeded");

        if (userRepository.findByLogin(dto.getLogin()).isPresent())
            throw new IllegalArgumentException("User with this login already exist");

        User user = userMapper.toEntity(dto, null);

        if (!dto.getPassword().equals(dto.getPasswordConfirm()))
            throw new IllegalArgumentException("Passwords do not match");
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setSecurityAnswer(passwordEncoder.encode(dto.getSecurityAnswer()));

        if (dto.getAddress() != null)
            user.setAddress(sensitiveDataEncryptor.encrypt(dto.getAddress()));
        if (dto.getPhone() != null)
            user.setPhone(sensitiveDataEncryptor.encrypt(dto.getPhone()));

        Role role = roleService.findRole(dto.getUserType());
        user.setRoles(singletonList(role));

        userRepository.create(user);
    }

    @Override
    @Transactional
    public void update(UserDto dto, Long id) {
        User user = userMapper.toEntity(dto, id);

        User currentUser = securityUtils.getCurrentUser();
        if (user.equals(currentUser)
                || securityUtils.hasRole(currentUser, ROLE_ADMIN))
            userRepository.update(user);
        else
            log.warn("Attempt to update user failed. Permission denied");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<User> foundUser = userRepository.getById(id);

        if (foundUser.isPresent()) {
            User currentUser = securityUtils.getCurrentUser();
            if (foundUser.get().equals(currentUser)
                    || securityUtils.hasRole(currentUser, ROLE_ADMIN))
                userRepository.delete(foundUser.get());
            else
                log.warn("Attempt to delete user failed. Permission denied");
        }
    }

    @Override
    @Transactional
    public UserDto getById(Long id) {
        Optional<User> foundUser = userRepository.getById(id);

        if (foundUser.isPresent()) {
            User currentUser = securityUtils.getCurrentUser();
            if (foundUser.get().equals(currentUser)
                    || securityUtils.hasRole(currentUser, ROLE_ADMIN))
                return userMapper.toDto(foundUser.get());
            else
                log.warn("Attempt to get user data failed. Permission denied");
        }
        return null;
    }

    @Override
    @Transactional
    public List<UserDto> getUsersPage(Integer page, Integer size) {
        int maxUsersPageSize = 100;
        if (page == null || page < 1)
            throw new IllegalArgumentException("Incorrect page number");
        if (size == null || size > maxUsersPageSize)
            throw new IllegalArgumentException("Incorrect page size");

        int positionFrom = (page - 1) * size;

        return userRepository.getUsersPage(positionFrom, size).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SensitiveDataDto getSensitiveData(Long id) {
        Optional<User> foundUser = userRepository.getById(id);

        if (foundUser.isPresent()) {
            User currentUser = securityUtils.getCurrentUser();
            if (foundUser.get().equals(currentUser)
                    || securityUtils.hasRole(currentUser, ROLE_ADMIN)) {
                User user = foundUser.get();

                SensitiveDataDto dto = new SensitiveDataDto();
                dto.setAddress(sensitiveDataEncryptor.decrypt(user.getAddress()));
                dto.setPhone(sensitiveDataEncryptor.decrypt(user.getPhone()));

                return dto;
            } else
                log.warn("Attempt to get user data failed. Permission denied");
        }

        return null;
    }
}
