package com.example.sse.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class SampleDataService {
    
    private Map<String,SampleData> requestCache = new HashMap<>(); 

    public SampleData generateData(String processId) {
        SampleData sd = 
            Optional
                .ofNullable(requestCache.get(processId))
                .orElseGet(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {}
                    return new SampleData(processId,ThreadLocalRandom.current().nextInt(100));
                });            
        requestCache.put(processId, sd);
        return sd;
    }

    public void clearCache(String processId) {
        requestCache.remove(processId);
    }

}
