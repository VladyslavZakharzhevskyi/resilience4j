package com.dataart.idle.resilience4j.controller;

import com.dataart.idle.resilience4j.service.SomeService;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class RateTimeLimiterController {
    private static final Logger LOG = LogManager.getLogger(RateTimeLimiterController.class);

    @Autowired
    private SomeService someService;
    @Autowired
    private RateLimiter rateLimiter;
    @Autowired
    private TimeLimiter timeLimiter;
    @Autowired
    private Retry retry;


    @RequestMapping(method = RequestMethod.GET, path = "/rateLimiter")
    public ResponseEntity<String> callServiceWithRateLimiter() {
        CheckedFunction0<String> rateLimiterDataRetrieval = () -> rateLimiter.executeCheckedSupplier(() -> someService.method1());
        CheckedFunction0<String> retryDataRetrieval = () -> retry.executeCheckedSupplier(() -> someService.method3());
        String response = Try.of(rateLimiterDataRetrieval)
                .getOrElseTry(retryDataRetrieval);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/timeLimiter")
    public ResponseEntity<String> callServiceWithTimeLimiter() throws Exception {
        String response = timeLimiter.executeFutureSupplier(() -> CompletableFuture.supplyAsync(() -> someService.method2()));
        return ResponseEntity.ok(response);
    }

}
