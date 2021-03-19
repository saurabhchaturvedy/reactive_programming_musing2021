package com.learn.reactive.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {


    @Test
    public void fluxTest()
    {
       Flux<String> springFlux = Flux.just("Spring","Spring Boot","Reactive Spring").
               concatWith(Flux.error(new RuntimeException("Exception occured"))).concatWith(Flux.just("After Error")).log();
       springFlux.subscribe(System.out::println,(e) -> System.err.println(e));
    }

    @Test
    public void fluxTestElements_WithoutError()
    {
       Flux<String> springFlux = Flux.just("Spring","Spring Boot","Reactive Spring").log();
        StepVerifier.create(springFlux).expectNext("Spring").expectNext("Spring Boot").expectNext("Reactive Spring").verifyComplete();

        // verifyComplete is same like subscribe
    }

    @Test
    public void fluxTestElements_WithError()
    {
        Flux<String> springFlux = Flux.just("Spring","Spring Boot","Reactive Spring").concatWith(Flux.error(new RuntimeException("Exception occured"))).log();
        StepVerifier.create(springFlux).expectNext("Spring").expectNext("Spring Boot").expectNext("Reactive Spring").expectErrorMessage("Exception occured").verify();

        // expectError and expectErrorMessage can't be invoked together
    }

    @Test
    public void fluxTestElementsCount_WithError()
    {
        Flux<String> springFlux = Flux.just("Spring","Spring Boot","Reactive Spring").concatWith(Flux.error(new RuntimeException("Exception occured"))).log();
        StepVerifier.create(springFlux).expectNextCount(3).expectErrorMessage("Exception occured").verify();

        // expectError and expectErrorMessage can't be invoked together
    }

    @Test
    public void monoTest()
    {
        Mono<String> stringMono = Mono.just("Spring");
        StepVerifier.create(stringMono.log()).expectNext("Spring").verifyComplete();
    }

    @Test
    public void monoTestError()
    {

        StepVerifier.create(Mono.error(new RuntimeException("Exception occured")).log()).verifyError();
    }
}
