package com.dataart.idle.resilience4j.controller;

import com.dataart.idle.resilience4j.model.DataTransferResponse;
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

import java.util.concurrent.CompletionStage;

@RestController
public class BulkHeadController {

    private static final Logger LOG = LogManager.getLogger(BulkHeadController.class);

    @Autowired
    private ThreadPoolBulkhead fixedThreadPoolBulkhead;
    @Autowired
    private Bulkhead semaphoreBulkhead;

    @RequestMapping(method = RequestMethod.POST, path = "/bulkhead")
    public ResponseEntity<String> runBulkhead(@RequestParam(name = "type") String type,
                                              @RequestParam(name = "threadCount") Integer threadCount) {
        if (type.equals("thread-pool")) {
            for (int i = 0; i < threadCount; i++) {
                //runnable
                fixedThreadPoolBulkhead.executeRunnable(new SimpleThread());

                //callable
                CompletionStage<DataTransferResponse> callable = fixedThreadPoolBulkhead.executeCallable(() -> {
                    return new DataTransferResponse("Wikipedia", new byte[1000], DataTransferResponse.Type.IMAGE);
                    //throw new RuntimeException("RuntimeException");
                });
                callable.exceptionally(throwable ->
                        new DataTransferResponse("Wikipedia", new byte[1000], DataTransferResponse.Type.RESOURCE));
                callable.thenAccept(response -> LOG.info("Callable has been executed, id: {}", response.getId()));

                //supplier
                CompletionStage<DataTransferResponse> supplier = fixedThreadPoolBulkhead.executeSupplier(() ->
                        new DataTransferResponse("Wikipedia", new byte[1000], DataTransferResponse.Type.TEXT));
                supplier.thenAccept(response -> LOG.info("Supplier has been executed, id: {}", response.getId()));
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
