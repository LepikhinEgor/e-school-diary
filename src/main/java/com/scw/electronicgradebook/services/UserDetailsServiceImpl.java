package com.scw.electronicgradebook.services;

import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.entities.SecurityUser;
import com.scw.electronicgradebook.domain.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
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
