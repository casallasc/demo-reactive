package com.example.demoreactive.services;

import com.example.demoreactive.model.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GreetingService {
    private final static String PATH_GREETINGS = "greetings";
    private final static String PATH_GREETING = "greetings/{id}";

    private final WebClient webClient;

    public Mono<Greeting> getGreeting(Integer greetingId) {
        return webClient.get()  // HTTP method
                .uri(PATH_GREETING, greetingId)  // path relative to base url
                .retrieve()
                .bodyToMono(Greeting.class);  // materialize
    }

    public Flux<Greeting> getGreetings() {
        return webClient.get()  // HTTP method
                .uri(PATH_GREETINGS)  // path relative to base url
                .retrieve()
                .bodyToFlux(Greeting.class);  // materialize
    }
}