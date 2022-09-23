package com.dataart.idle.resilience4j.config;

import com.dataart.idle.resilience4j.properties.BulkHeadGlobalProperties;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class BulkheadCConfig {

    public static final String THREAD_POOL_BULKHEAD = "ThreadPoolBulkhead";
    public static final String SEMAPHORE_BULKHEAD = "SemaphoreBulkhead";

    @Autowired
    private BulkHeadGlobalProperties properties;

    @Bean
    public ThreadPoolBulkhead initThreadPoolBulkhead() {
        final Integer coreThreadPoolSize = properties.getInitCoreThreadPoolSize();
        int maxThreadPoolSize = coreThreadPoolSize + 2;
        final Integer queueCapacity = properties.getInitQueueCapacity();

        ThreadPoolBulkheadConfig config = ThreadPoolBulkheadConfig.custom()
                .maxThreadPoolSize(maxThreadPoolSize)
                .coreThreadPoolSize(coreThreadPoolSize)
                .queueCapacity(queueCapacity)
                .build();

        ThreadPoolBulkheadRegistry registry = ThreadPoolBulkheadRegistry.of(config);

        return registry.bulkhead(THREAD_POOL_BULKHEAD);
    }

    @Bean
    public Bulkhead initSemaphoreBulkhead() {
        Integer maxConcurrentCalls = properties.getSemaphoreMaxConcurrentCalls();
        Long maxWaitDuration = properties.getSemaphoreMaxWaitDuration();

        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(maxConcurrentCalls)
                .maxWaitDuration(Duration.ofMillis(maxWaitDuration))
                .build();

        BulkheadRegistry registry = BulkheadRegistry.of(config);

        return registry.bulkhead(SEMAPHORE_BULKHEAD);
    }
}
