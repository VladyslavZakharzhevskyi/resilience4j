package com.dataart.idle.resilience4j.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestTemplate;

public class APICallThread implements Runnable {
    private static final Logger LOG = LogManager.getLogger(APICallThread.class);
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private final String apiEndpoint;

    public APICallThread(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    @Override
    public void run() {
        LOG.info("Executing call for apiEndpoint: {}", apiEndpoint);
        String response = REST_TEMPLATE.getForObject(apiEndpoint, String.class);
        LOG.info("Response from apiEndpoint has been received: {}", response);
    }
}
