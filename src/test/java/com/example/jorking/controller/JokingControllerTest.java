package com.example.jorking.controller;

import com.example.jorking.model.Joking;
import com.example.jorking.service.JokingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JokingControllerTest {

    @Mock
    private JokingService jokingService;

    @InjectMocks
    private JokingController controller;

    @Test
    void getJokes_shouldReturnSuccess_whenServiceQueryJokeSuccess() {
        // given
        when(jokingService.getJokes("hello"))
                .thenReturn(
                        List.of(
                                new Joking("1", "hello world"),
                                new Joking("2", "Hello Vietnam")
                        )
                );

        // when
        ResponseEntity<List<Joking>> response = controller.getJokes("hello");

        // then
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
    }

    @Test
    void getJokes_shouldReturnError_whenServiceQueryJokeFailure() {
        // given
        when(jokingService.getJokes(anyString()))
                .thenThrow(NullPointerException.class);

        // when
        //ResponseEntity<List<Joking>> response = controller.getJokes("hello");

        // then
       assertThrows(NullPointerException.class, () -> {
           controller.getJokes("hello");
       });
    }
}