package com.scw.electronicgradebook.domain.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class SecurityUser implements UserDetails {

    private String login;

    private String password;

    private Collection<GrantedAuthority> authorities;

    public SecurityUser(User user) {
        login = user.getLogin();
        password = user.getPassword();

        if (user.getRoles() != null) {
            authorities = user.getRoles().stream()
                    .flatMap(role -> role.getPrivileges().stream())
                    .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
                    .collect(Collectors.toSet());
            user.getRoles()
                    .forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        } else
            authorities = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
