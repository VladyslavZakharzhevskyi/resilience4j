package com.dataart.idle.resilience4j.controller;

import com.dataart.idle.resilience4j.util.APICallThread;
import com.dataart.idle.resilience4j.util.SimpleThread;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BulkHeadController {

    private static final Logger LOG = LogManager.getLogger(BulkHeadController.class);

    @Autowired
    private ThreadPoolBulkhead fixedThreadPoolBulkhead;
    @Autowired
    private Bulkhead semaphoreBulkhead;


    @RequestMapping(method = RequestMethod.POST, path = "/bulkhead")
    public ResponseEntity<String> runBulkhead(@RequestParam(name = "type") String type,
                                              @RequestParam(name = "threadCount") Integer threadCount,
                                              @RequestParam(name = "apiEndpoint") String apiEndpoint) {
        if (type.equals("thread-pool")) {
            for (int i = 0; i < threadCount; i++) {
                fixedThreadPoolBulkhead.executeRunnable(new APICallThread(apiEndpoint));
            }

            return ResponseEntity.ok("Bulkhead with type thread-pool has been launched.");
        } else if (type.equals("semaphore")) {
            for (int i = 0; i < threadCount; i++) {
                semaphoreBulkhead.executeRunnable(new SimpleThread());
            }
            return ResponseEntity.ok("Bulkhead with type semaphore has been executed.");
        }

        return ResponseEntity.ok("Can't determine type. Available types are: thread-pool and semaphore values");
    }

}
