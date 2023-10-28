package com.example.demoreactive.controllers;

import com.example.demoreactive.aspects.HeaderValidator;
import com.example.demoreactive.model.Config;
import com.example.demoreactive.model.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.example.demoreactive.services.GreetingService;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class GreetingController {

    private final GreetingService greetingService;
    private final Config config;


    @GetMapping("greetings/{greetingId}")
    @HeaderValidator
    public Mono<Greeting> getGreeting(ServerWebExchange serverWebExchange, @PathVariable Integer greetingId) {
        return greetingService.getGreeting(greetingId);
    }

    @GetMapping("greetings")
    public Flux<Greeting> getGreetings() {
        return greetingService.getGreetings();
    }

    @GetMapping("configs")
    public Mono<Config> getConfig() {
        return Mono.just(config);
    }
}
