package com.dataart.idle.resilience4j.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "reselience4j.bulkhead")
public class BulkHeadGlobalProperties {

    private ThreadPoolProperties threadPool;
    private SemaphoreProperties semaphore;
    private static Map<String, Object> properties;

    @Data
    public static class ThreadPoolProperties {
        private Integer coreThreadPoolSize;
        private Integer queueCapacity;
    }

    @Data
    private static class SemaphoreProperties {
        private Integer maxConcurrentCalls;
        private Long maxWaitDuration;
    }

    public void setProperties(Map<String, Object> properties) {
        BulkHeadGlobalProperties.properties = properties;
    }

    public Integer getInitCoreThreadPoolSize() {
        return threadPool.coreThreadPoolSize;
    }

    public Integer getInitQueueCapacity() {
        return threadPool.queueCapacity;
    }

    public Integer getSemaphoreMaxConcurrentCalls() {
        return semaphore.maxConcurrentCalls;
    }

    public Long getSemaphoreMaxWaitDuration() {
        return semaphore.maxWaitDuration;
    }

    public static Long getThreadSleepTime() {
        return Long.valueOf((Integer) properties.get("thread-sleep-time"));
    }

}
