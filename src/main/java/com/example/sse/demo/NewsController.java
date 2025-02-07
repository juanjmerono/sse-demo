package com.example.sse.demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class NewsController {
    
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @GetMapping("/news")
    public SseEmitter streamSseEvents() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        executor.execute(() -> {
            try {
                Thread.sleep(10000); // simulate delay
                emitter.send(new SampleData("Mensaje de texto", 55));
                emitter.complete();
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }
    

}
