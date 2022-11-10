package com.example.jorking.repository;

import com.example.jorking.model.Joking;
import com.example.jorking.model.QuerryJokes;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;

import java.util.List;
import java.util.Objects;

@Repository
public class JokingRepository {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CircuitBreakerFactory circuitBreakerFactory;

    public List<Joking> getJokes(String keyWord) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        String url = "https://api.chucknorris.io/jokes/search?query=" + keyWord;
        ResponseEntity<QuerryJokes> forEntity = restTemplate.getForEntity(url, QuerryJokes.class);
        return circuitBreaker.run(() -> Objects.requireNonNull(forEntity.getBody()).getResult());
    }


}
