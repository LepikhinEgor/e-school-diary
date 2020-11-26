package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.RoleRepository;
import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.dto.RegistrationDto;
import com.scw.electronicgradebook.domain.dto.UserDto;
import com.scw.electronicgradebook.domain.entities.Role;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.domain.mappers.UserMapper;
import com.scw.electronicgradebook.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void register(RegistrationDto dto) {
        User user = userMapper.toEntity(dto, null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = findRole(dto.getUserDto().getUserType());
        user.setRoles(singletonList(role));

        userRepository.create(user);
    }

    private Role findRole(String userType) {
        String rolePrefix = "ROLE_";
        Optional<Role> foundRole = roleRepository.getByName(rolePrefix + userType);

        return foundRole.orElseThrow(() -> new IllegalArgumentException("Role not found for user type"));
    }

    @Override
    @Transactional
    public void update(UserDto dto, Long id) {
        User user = userMapper.toEntity(dto, id);

        userRepository.update(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<User> foundUser = userRepository.getById(id);

        foundUser.ifPresent(user -> userRepository.delete(user));
    }

    @Override
    @Transactional
    public Optional<UserDto> getById(Long id) {
        Optional<User> foundUser = userRepository.getById(id);

        return foundUser.map(user -> userMapper.toDto(user));
    }
}
