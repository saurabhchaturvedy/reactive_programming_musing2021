package com.learn.reactive.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoErrorTest {


    @Test
    public void fluxErrorHandling() {
        Flux<String> stringFlux = Flux.just("A", "B", "C").concatWith(Flux.error(new RuntimeException("Exception occured"))).concatWith(Flux.just("D")).onErrorResume((e) -> {
            System.out.println("Exception is " + e);
            return Flux.just("default", "default1");
        });

        StepVerifier.create(stringFlux.log()).expectSubscription().expectNext("A", "B", "C").expectNext("default", "default1").verifyComplete();
    }

    @Test
    public void fluxErrorHandling_ErrorMap() {
        Flux<String> stringFlux = Flux.just("A", "B", "C").concatWith(Flux.error(new RuntimeException("Exception occured"))).concatWith(Flux.just("D")).onErrorMap((e) -> new CustomException(e.getMessage()));

        StepVerifier.create(stringFlux.log()).expectSubscription().expectNext("A", "B", "C").expectError(CustomException.class).verify();
    }

    @Test
    public void fluxErrorHandling_ErrorMap_withRetry() {
        Flux<String> stringFlux = Flux.just("A", "B", "C").concatWith(Flux.error(new RuntimeException("Exception occured"))).concatWith(Flux.just("D")).onErrorMap((e) -> new CustomException(e.getMessage())).retry(2);

        StepVerifier.create(stringFlux.log()).expectSubscription().expectNext("A", "B", "C").expectNext("A", "B", "C").expectNext("A", "B", "C").expectError(CustomException.class).verify();
    }


    @Test
    public void fluxErrorHandling_ErrorMap_withRetry_Backoff() {
        Flux<String> stringFlux = Flux.just("A", "B", "C").concatWith(Flux.error(new RuntimeException("Exception occured"))).concatWith(Flux.just("D")).onErrorMap((e) -> new CustomException(e.getMessage()));

        StepVerifier.create(stringFlux.log()).expectSubscription().expectNext("A", "B", "C").expectNext("A", "B", "C").expectNext("A", "B", "C").expectError(CustomException.class).verify();
    }
}
