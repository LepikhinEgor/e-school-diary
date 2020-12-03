package com.scw.electronicgradebook.services.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class HttpFloodProtector {

    @Value("${application.requests.limit}")
    private int requestsPerMinuteLimit;

    private LoadingCache<String, Integer> requestsByIps;

    public HttpFloodProtector() {

        requestsByIps = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public Integer load(String s) {
                        return 0;
                    }
                });
    }

    public boolean ipBlocked(String ip) {
        try {
            Integer requestsPerMinute = requestsByIps.get(ip);
            requestsPerMinute++;

            requestsByIps.put(ip, requestsPerMinute);

            return requestsPerMinute > requestsPerMinuteLimit;
        } catch (Exception e) {
            return false;
        }
    }
}
