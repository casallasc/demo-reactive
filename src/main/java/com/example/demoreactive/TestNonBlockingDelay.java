package com.example.demoreactive;

import reactor.core.publisher.Mono;

import java.time.Duration;

public class TestNonBlockingDelay {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("inicio");
        //Mono<String>
        Mono.just("Carlos")
                .map(value -> value + " Casallas" )
                .delayElement(Duration.ofSeconds(5))
                .doOnNext(value -> System.out.println("name: " + value))
                .subscribe();
        System.out.println("fin");
        Thread.sleep(6000);

    }
}
