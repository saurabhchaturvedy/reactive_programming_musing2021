package com.learn.reactive.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoBackpressureTest {


    @Test
    public void backpressureTest() {
        Flux<Integer> integerFlux = Flux.range(1, 10).log();
        StepVerifier.create(integerFlux).expectSubscription().thenRequest(1).expectNext(1).thenRequest(2).expectNext(2).thenCancel().verify();
    }


    @Test
    public void backPressure() {
        Flux<Integer> integerFlux = Flux.range(1, 10).log();
        integerFlux.subscribe((element) -> System.out.println("Element is " + element), (e) -> System.out.println("Exception is " + e), () -> System.out.println("Done"), (subscription -> subscription.request(2)));
    }


    @Test
    public void backPressure_CANCEL() {
        Flux<Integer> integerFlux = Flux.range(1, 10).log();
        integerFlux.subscribe((element) -> System.out.println("Element is " + element), (e) -> System.out.println("Exception is " + e), () -> System.out.println("Done"), (subscription -> subscription.cancel()));
    }

    @Test
    public void backPressure_CUSTOMIZED() {
        Flux<Integer> integerFlux = Flux.range(1, 10).log();
        integerFlux.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnNext(Integer value) {
                request(1);
                System.out.println("value received is " + value);
                if (value == 4) {
                    cancel();
                }
            }
        });
    }
}
