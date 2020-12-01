package com.scw.electronicgradebook.domain.validators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;

@Slf4j
@Service
public class SSRFValidator {
    private String[] whiteListProtocol = {"https"};

    public boolean isValid(URL url) throws MalformedURLException, UnknownHostException {
        try {
            if (!checkProtocol(url))
                return false;

            InetAddress ipAddress = InetAddress.getByName(url.getHost());

            return !ipAddress.isAnyLocalAddress() && !ipAddress.isLoopbackAddress()
                    && !ipAddress.isLinkLocalAddress() && !ipAddress.isSiteLocalAddress();
        } catch (Exception e) {
            log.error("Error during string validation");
            return false;
        }
    }

    private boolean checkProtocol(URL url) {
        return Arrays.stream(whiteListProtocol)
                .anyMatch(s -> url.getProtocol().startsWith(s));
    }
}
