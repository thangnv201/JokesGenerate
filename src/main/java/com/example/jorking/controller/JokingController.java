package com.example.jorking.controller;

import com.example.jorking.model.Joking;
import com.example.jorking.service.JokingService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

@RestController
public class JokingController {

    @Autowired
    private JokingService jokingService;

    private final Bucket bucket;

    public JokingController() {
        Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder().addLimit(limit).build();
    }

    @GetMapping("jokes/{keyword}")
    public ResponseEntity<List<Joking>> getJokes(@PathVariable String keyword) {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(jokingService.getJokes(keyword));

        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}
