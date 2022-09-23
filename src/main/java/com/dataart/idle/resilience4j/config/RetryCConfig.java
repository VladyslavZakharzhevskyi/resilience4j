package com.dataart.idle.resilience4j.config;

import com.dataart.idle.resilience4j.properties.RetryGlobalProperties;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RetryCConfig {

    public final static String RETRY = "Retry";

    @Autowired
    private RetryGlobalProperties properties;

    @Bean
    public Retry initRetry() {
        Integer maxAttempts = properties.getMaxAttempts();
        Long waitDuration = properties.getWaitDuration();

        RetryConfig config = RetryConfig.custom()
                .maxAttempts(maxAttempts)
                .waitDuration(Duration.ofMillis(waitDuration))
                .retryExceptions(RequestNotPermitted.class)
                .failAfterMaxAttempts(true)
                .build();

        RetryRegistry registry = RetryRegistry.of(config);

        return registry.retry(RETRY);
    }

}
