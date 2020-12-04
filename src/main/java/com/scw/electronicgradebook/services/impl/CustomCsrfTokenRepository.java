package com.scw.electronicgradebook.services.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CustomCsrfTokenRepository implements CsrfTokenRepository {

    private static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";

    private static final String DEFAULT_CSRF_HEADER_NAME = "X-CSRF-TOKEN";

    private LoadingCache<String, String> sessionsTokens;

    public CustomCsrfTokenRepository() {
        super();
        sessionsTokens = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        return "";
                    }
                });
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken(DEFAULT_CSRF_HEADER_NAME,
                DEFAULT_CSRF_PARAMETER_NAME,
                UUID.randomUUID().toString());
    }

    @Override
    @Transactional
    public void saveToken(CsrfToken csrfToken,
                          HttpServletRequest request,
                          HttpServletResponse httpServletResponse) {
        if (csrfToken != null) {
            String jSessionId = request.getSession().getId();

            sessionsTokens.put(jSessionId, csrfToken.getToken());
        }
    }

    @Override
    @Transactional
    public CsrfToken loadToken(HttpServletRequest request) {
        try {
            String token = sessionsTokens.get(request.getSession().getId());
            return new DefaultCsrfToken(DEFAULT_CSRF_HEADER_NAME,
                    DEFAULT_CSRF_PARAMETER_NAME,
                    token);
        } catch (Exception e) {
            log.error("Error loading csrf token");
            return null;
        }
    }
}
