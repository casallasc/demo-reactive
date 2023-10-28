package com.example.demoreactive.services;

import com.example.demoreactive.model.Greeting;
import io.vavr.control.Either;
import io.vavr.control.Try;
import reactor.core.publisher.Mono;

import java.util.Random;

public class HelloService {

    public Mono<Greeting> sayHello(Greeting greeting) {
        return Mono.just(new Random().nextBoolean())
                .map(condition -> generateGreeting(condition, greeting))
                .onErrorResume(error -> Mono.just(Greeting.builder().greeting(error.getMessage()).build()));
    }

    private Try<Greeting> sayHelloTryExample(Greeting greeting) {
        return Try.success(new Random().nextBoolean())
                .map(condition -> generateGreeting(condition, greeting))
                .recover(error -> Greeting.builder().greeting(error.getMessage()).build());
    }

    private Either<String, Greeting> sayHelloEither(Greeting greeting) {
        return Either.<String, Boolean>right(new Random().nextBoolean())
                .flatMap(condition -> generateGreetingEither(condition, greeting));
    }

    private Greeting generateGreeting(boolean condition, Greeting greeting) {
         if (condition) {
             return greeting.toBuilder().greeting("Hello " + greeting.getName()).build();
         } else {
             throw new RuntimeException("Opss, error greeting");
         }
    }

    private Either<String, Greeting> generateGreetingEither(boolean condition, Greeting greeting) {
        if (condition) {
            return Either.right(greeting.toBuilder().greeting("Hello " + greeting.getName()).build());
        } else {
            return Either.left("Opss, error greeting");
        }
    }

    public Mono<Greeting> sayHelloMaybeEmpty(Greeting greeting) {
        return Mono.just(new Random().nextBoolean())
                .filter(Boolean::booleanValue)
                .map(ignored -> greeting.toBuilder().greeting("Hello " + greeting.getName()).build());
    }

    public Mono<Greeting> sayHelloMaybeError(Greeting greeting) {
        return Mono.just(new Random().nextBoolean())
                .map(condition -> generateGreeting(condition, greeting));
    }
}
