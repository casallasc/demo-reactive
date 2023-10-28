package com.example.demoreactive;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class TestBlockingDelay {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("inicio");
        //Mono<String>
        Mono.just("Carlos")
                .map(value -> value + " Casallas" )
                .map(value -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return value;
                })
                .doOnNext(value -> System.out.println("name: " + value))
                .subscribeOn(Schedulers.parallel())
                .subscribe();
        System.out.println("fin");
        Thread.sleep(6000);

    }
}
