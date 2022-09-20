package com.dataart.idle.resilience4j.util;

import com.dataart.idle.resilience4j.properties.BulkHeadGlobalProperties;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SimpleThread implements Runnable {

    private final Long sleepTime = BulkHeadGlobalProperties.getThreadSleepTime();

    @Override
    public void run() {
        try {
            log.info("Starting...");
            Thread.sleep(sleepTime);
            log.info("Finish.");
        } catch (InterruptedException e) {
            log.error("Execution occurred due interrupting");
        }
    }
}
