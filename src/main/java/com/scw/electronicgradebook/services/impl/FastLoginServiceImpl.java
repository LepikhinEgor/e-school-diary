package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.entities.Role;
import com.scw.electronicgradebook.domain.entities.SecurityUser;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.services.FastLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
