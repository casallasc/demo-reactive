package com.example.demoreactive.services;

import com.example.demoreactive.model.Greeting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class HelloServiceTest {

    private HelloService helloService;
    @BeforeEach
    public void setUp() {
        helloService = new HelloService();
    }
    @Test
    void testHello() {
        Mono<Greeting> response = helloService.sayHello(Greeting.builder().name("name").build());
        StepVerifier.create(response).expectNextMatches(greeting -> greeting.getGreeting().equals("Hello name")).verifyComplete();
    }
}