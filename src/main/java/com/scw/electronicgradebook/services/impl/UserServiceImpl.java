package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.RoleRepository;
import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.dto.RegistrationDto;
import com.scw.electronicgradebook.domain.dto.UserDto;
import com.scw.electronicgradebook.domain.entities.Role;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.domain.mappers.UserMapper;
import com.scw.electronicgradebook.services.SecurityUtils;
import com.scw.electronicgradebook.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.scw.electronicgradebook.domain.enums.SecurityRole.ROLE_ADMIN;
import static java.util.Collections.singletonList;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    public void register(RegistrationDto dto) {
        User user = userMapper.toEntity(dto, null);

        if (!dto.getPassword().equals(dto.getPasswordConfirm()))
            throw new IllegalArgumentException("Passwords do not match");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = findRole(dto.getUserType());
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


    private Role findRole(String userType) {
        String rolePrefix = "ROLE_";
        Optional<Role> foundRole = roleRepository.getByName(rolePrefix + userType.toUpperCase());

        return foundRole.orElseThrow(() -> new IllegalArgumentException("Role not found for user type"));
    }
}
