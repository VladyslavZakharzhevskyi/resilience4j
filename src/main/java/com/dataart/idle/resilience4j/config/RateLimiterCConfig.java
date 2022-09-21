package com.dataart.idle.resilience4j.config;

import com.dataart.idle.resilience4j.properties.RateTimeLimiterGlobalProperties;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterCConfig {

    public final static String DEFAULT_RATE_LIMITER = "RateLimiter";

    @Autowired
    private RateTimeLimiterGlobalProperties properties;

    @Bean
    public RateLimiter initRateLimiter() {
        RateLimiterConfig defaultConfig = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMillis(properties.getLimitRefreshPeriod()))
                .limitForPeriod(properties.getLimitForPeriod())
                .timeoutDuration(Duration.ofMillis(properties.getTimeoutDuration()))
                .build();

        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(defaultConfig);

        return rateLimiterRegistry.rateLimiter(DEFAULT_RATE_LIMITER);
    }

}
