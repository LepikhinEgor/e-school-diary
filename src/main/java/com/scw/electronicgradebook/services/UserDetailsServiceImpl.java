package com.scw.electronicgradebook.services;

import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.entities.SecurityUser;
import com.scw.electronicgradebook.domain.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            Optional<User> foundUser = userRepository.findByLogin(login);

            if (foundUser.isPresent())
                return new SecurityUser(foundUser.get());
            else {
                log.warn("Failed authentication attempt for user with login " + login);
                throw new UsernameNotFoundException("User with this login or password was not found");
            }
        } catch (Exception e) {
            log.error("Error while searching for user ", e);

            throw new UsernameNotFoundException("User with this login or password was not found", e);
        }
    }
}
