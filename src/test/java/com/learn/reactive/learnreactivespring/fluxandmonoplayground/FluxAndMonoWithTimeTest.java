package com.learn.reactive.learnreactivespring.fluxandmonoplayground;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoWithTimeTest {


    @Test
    @SneakyThrows
    public void infiniteSequence()
    {
        Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(200)).log();
        infiniteFlux.subscribe(System.out::println);
        Thread.sleep(3000);

    }


    @Test
    @SneakyThrows
    public void infiniteSequenceTest()
    {
        Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(200)).take(3).log();
        StepVerifier.create(infiniteFlux).expectSubscription().expectNext(0L,1L,2L).verifyComplete();

    }

    @Test
    @SneakyThrows
    public void infiniteSequenceTest_MAP()
    {
        Flux<Integer> infiniteFlux = Flux.interval(Duration.ofMillis(200)).map(l->l.intValue()).take(3).log();
        StepVerifier.create(infiniteFlux).expectSubscription().expectNext(0,1,2).verifyComplete();

    }

    @Test
    @SneakyThrows
    public void infiniteSequenceTest_DELAY()
    {
        Flux<Integer> infiniteFlux = Flux.interval(Duration.ofMillis(200)).delayElements(Duration.ofSeconds(1)).map(l->l.intValue()).take(3).log();
        StepVerifier.create(infiniteFlux).expectSubscription().expectNext(0,1,2).verifyComplete();

    }
}
