package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.entities.SecurityUser;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.services.FastLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FastLoginServiceImpl implements FastLoginService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void loginAsTeacher() {
        User user = userRepository.getRandomTeacher();

        UserDetails securityUser = new SecurityUser(user);

        var authentication = new UsernamePasswordAuthenticationToken(securityUser, null,
                securityUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
