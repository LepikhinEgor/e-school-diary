package com.scw.electronicgradebook.services.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.scw.electronicgradebook.services.LoginAttemptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

    @Value("${application.login.attempts.max}")
    private int maxLoginAttempts;

    private LoadingCache<String, Integer> ipAttempts;

    public LoginAttemptServiceImpl() {
        super();
        ipAttempts = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build(new CacheLoader<>() {
                    @Override
                    public Integer load(String s) {
                        return 0;
                    }
                });
    }

    @Override
    @EventListener(AuthenticationSuccessEvent.class)
    public void loginSucceeded(AuthenticationSuccessEvent e) {
        ipAttempts.invalidate(getIpFromEvent(e));
    }

    @Override
    @EventListener(AuthenticationFailureBadCredentialsEvent.class)
    public void loginFailed(AuthenticationFailureBadCredentialsEvent event) {
        String ip = getIpFromEvent(event);

        int attempts = 0;
        try {
            attempts = ipAttempts.get(ip);
            attempts++;

            ipAttempts.put(ip, attempts);
        } catch (ExecutionException e) {
            log.error("An error occurred when checking for brute force");
        }
    }

    @Override
    public boolean isBlocked(String ip) {
        try {
            return ipAttempts.get(ip) > maxLoginAttempts;
        } catch (ExecutionException e) {
            log.error("An error occurred when checking for brute force");

            return false;
        }
    }

    private String getIpFromEvent(AbstractAuthenticationEvent event) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails)
                event.getAuthentication().getDetails();

        return auth.getRemoteAddress();
    }
}
