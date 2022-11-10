package com.example.jorking.service;

import com.example.jorking.model.Joking;
import com.example.jorking.repository.JokingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JokingService {
    @Autowired
    private JokingRepository jokingRepository;

    public List<Joking> getJokes(String keyword) {
        List<Joking> list =  jokingRepository.getJokes(keyword);
        return list;
    }
}
