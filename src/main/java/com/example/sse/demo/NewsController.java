package com.example.sse.demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class NewsController {
    
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    private SampleDataService sampleDataService;

    @GetMapping("/news")
    public SseEmitter streamSseEvents(@RequestParam(name = "pid") String processId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        executor.execute(() -> {
            try {
                emitter.send(sampleDataService.generateData(processId));
                emitter.complete();
                sampleDataService.clearCache(processId);
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }
    

}
