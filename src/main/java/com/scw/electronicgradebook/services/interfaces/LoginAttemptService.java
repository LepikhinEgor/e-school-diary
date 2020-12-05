package com.scw.electronicgradebook.services.interfaces;

import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

public interface LoginAttemptService {

    void loginSucceeded(AuthenticationSuccessEvent event);

    void loginFailed(AuthenticationFailureBadCredentialsEvent event);

    boolean isBlocked(String ip);
}
