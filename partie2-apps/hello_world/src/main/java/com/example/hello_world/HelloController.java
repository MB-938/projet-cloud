package com.example.hello_world;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.time.LocalDateTime;

@RestController
public class HelloController {

    @GetMapping("/")
    public Map<String, String> helloWorld() {
        return Map.of(
            "message", "Hello World depuis Spring Boot !",
            "timestamp", LocalDateTime.now().toString(),
            "status", "OK"
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}