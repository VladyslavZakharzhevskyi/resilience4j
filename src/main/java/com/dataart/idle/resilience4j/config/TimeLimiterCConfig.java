package com.dataart.idle.resilience4j.config;

import com.dataart.idle.resilience4j.properties.RateTimeLimiterGlobalProperties;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class TimeLimiterCConfig {

    public final static String DEFAULT_TIME_LIMITER = "TimeLimiter";


    @Autowired
    private RateTimeLimiterGlobalProperties properties;

    @Bean
    public TimeLimiter initTimeLimiter() {
        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .cancelRunningFuture(properties.isCancelRunningFuture())
                .timeoutDuration(Duration.ofMillis(properties.getTimeoutDurationForTimeLimiter()))
                .build();

        TimeLimiterRegistry registry = TimeLimiterRegistry.of(config);

        return registry.timeLimiter(DEFAULT_TIME_LIMITER);
    }

}
