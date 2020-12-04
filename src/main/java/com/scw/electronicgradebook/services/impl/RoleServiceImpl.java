package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.RoleRepository;
import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.entities.Role;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void addRole(Long userId, String role) {
        Optional<User> foundUser = userRepository.getById(userId);

        Optional<Role> foundRole = roleRepository.getByName(role);
        if (foundRole.isPresent() && foundUser.isPresent()) {
            User user = foundUser.get();
            user.getRoles().add(foundRole.get());

            userRepository.update(user);
        }
    }

    @Override
    @Transactional
    public void removeRole(Long userId, String role) {
        Optional<User> foundUser = userRepository.getById(userId);

        if (foundUser.isPresent()) {
            User user = foundUser.get();
            user.getRoles()
                    .removeIf(userRole -> !userRole.getName().equals(role));

            userRepository.update(user);
        }
    }

    @Override
    @Transactional
    public Role findRole(String userType) {
        String rolePrefix = "ROLE_";
        Optional<Role> foundRole = roleRepository.getByName(rolePrefix + userType.toUpperCase());

        return foundRole.orElseThrow(() -> new IllegalArgumentException("Role not found for user type"));
    }
}
