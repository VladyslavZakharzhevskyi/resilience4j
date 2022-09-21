package com.dataart.idle.resilience4j.controller;

import com.dataart.idle.resilience4j.service.SomeService;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.timelimiter.TimeLimiter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class LimiterController {
    private static final Logger LOG = LogManager.getLogger(LimiterController.class);

    @Autowired
    private SomeService someService;
    @Autowired
    private RateLimiter rateLimiter;
    @Autowired
    private TimeLimiter timeLimiter;

    @RequestMapping(method = RequestMethod.GET, path = "/rateLimiter")
    public ResponseEntity<String> callServiceWithRateLimiter() {
        String response = null;
        try {
            response = rateLimiter.executeCheckedSupplier(() -> someService.method1());
        } catch (Throwable e) {
            LOG.error("Exception occurred: {}", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/timeLimiter")
    public ResponseEntity<String> callServiceWithTimeLimiter() {
        String response = null;
        try {
            response = timeLimiter.executeFutureSupplier(() -> CompletableFuture.supplyAsync(() -> someService.method2()));
        } catch (Exception e) {
            LOG.error("Exception occurred: {}", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

}
