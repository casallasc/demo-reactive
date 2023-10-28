package com.example.demoreactive.controllers;

import com.example.demoreactive.model.Greeting;
import com.example.demoreactive.services.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final HelloService helloService;

    @PostMapping("/hello")
    public Mono<Greeting> sayHello(@RequestBody Greeting greetingRequest) {
        return helloService.sayHello(greetingRequest)
                .doOnNext(greeting -> System.out.println("on next greeting: " + greeting.getGreeting()));
    }

    @PostMapping("/hello-maybe-empty")
    public Mono<Greeting> sayHelloMaybeEmpty(@RequestBody Greeting greetingRequest) {
        return helloService.sayHelloMaybeEmpty(greetingRequest)
                .doOnNext(greeting -> System.out.println("on next greeting: " + greeting.getGreeting()))
                .doOnSuccess(greeting -> System.out.println("on success greeting!"));
    }

    @PostMapping("/hello-maybe-error")
    public Mono<Greeting> sayHelloMaybeError(@RequestBody Greeting greetingRequest) {
        return helloService.sayHelloMaybeError(greetingRequest)
                .doOnNext(greeting -> System.out.println("on next greeting: " + greeting.getGreeting()))
                .doOnError(error -> System.out.println("on error: " + error.getMessage()));
    }
}
