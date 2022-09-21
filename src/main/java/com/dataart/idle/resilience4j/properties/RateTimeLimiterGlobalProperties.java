package com.dataart.idle.resilience4j.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("reselience4j")
public class RateTimeLimiterGlobalProperties {

    private RateLimiterProperties rateLimiter;
    private TimeLimiterProperties timeLimiter;

    @Data
    private static class RateLimiterProperties {
        private Integer limitForPeriod;
        private Long limitRefreshPeriod;
        private Long timeoutDuration;
    }

    @Data
    private static class TimeLimiterProperties {
        private Long timeoutDuration;
        private Boolean cancelRunningFuture;
    }

    public Integer getLimitForPeriod() {
        return rateLimiter.limitForPeriod;
    }

    public Long getLimitRefreshPeriod() {
        return rateLimiter.limitRefreshPeriod;
    }

    public Long getTimeoutDuration() {
        return rateLimiter.timeoutDuration;
    }

    public Long getTimeoutDurationForTimeLimiter() {
        return timeLimiter.timeoutDuration;
    }

    public Boolean isCancelRunningFuture() {
        return timeLimiter.cancelRunningFuture;
    }

}
