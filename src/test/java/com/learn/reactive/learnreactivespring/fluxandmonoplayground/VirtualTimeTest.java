package com.learn.reactive.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

public class VirtualTimeTest {


    @Test
    public void withoutVirtualTime()
    {
        Flux<Long> take = Flux.interval(Duration.ofSeconds(1)).take(3);
        StepVerifier.create(take.log()).expectSubscription().expectNext(0L,1L,2L).verifyComplete();
    }

    @Test
    public void testingWithVirtualTime()
    {
        VirtualTimeScheduler.getOrSet();
        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1)).take(3);
        StepVerifier.withVirtualTime(()->longFlux.log()).expectSubscription().thenAwait(Duration.ofSeconds(3)).expectNext(0L,1L,2L).verifyComplete();
    }
}
