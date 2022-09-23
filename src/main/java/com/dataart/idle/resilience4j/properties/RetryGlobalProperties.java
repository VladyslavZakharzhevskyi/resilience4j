package com.dataart.idle.resilience4j.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("reselience4j.retry")
public class RetryGlobalProperties {
    private Integer maxAttempts;
    private Long waitDuration;
}
