package com.dataart.idle.resilience4j.service;

import com.dataart.idle.resilience4j.controller.BulkHeadController;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SomeServiceImpl implements SomeService {
    private static final Logger LOG = LogManager.getLogger(SomeServiceImpl.class);

    private final AtomicInteger count = new AtomicInteger();

    @Override
    public String method1() {
        int value = count.incrementAndGet();
        return String.format("Successfully executed method %s times.", value);
    }

    @Override
    public String method2()  {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOG.error("Interrupted thread exception occurred");
        }
        return "Successfully executed method 2.";
    }
}
