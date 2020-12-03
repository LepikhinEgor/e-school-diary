package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.services.SecurityUtils;
import com.scw.electronicgradebook.services.impl.HttpFloodProtector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class HttpFloodFilter extends GenericFilterBean {

    private final HttpFloodProtector httpFloodProtector;

    private final SecurityUtils securityUtils;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws ServletException, IOException {
        if (servletRequest instanceof HttpServletRequest) {
            var httpRequest = (HttpServletRequest) servletRequest;
            String ip = securityUtils.getClientIP(httpRequest);

            if (httpFloodProtector.ipBlocked(ip)) {
                logger.warn("To many requests from ip " + ip);
                throw new ServletException("To many requests");
            }

            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}
