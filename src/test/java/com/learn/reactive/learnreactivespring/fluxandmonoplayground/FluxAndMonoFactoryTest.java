package com.learn.reactive.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FluxAndMonoFactoryTest {
   
    
    List<String> names = Arrays.asList("Adam","Anna","Jack","Jenny");

    @Test
    public void fluxUsingIterable()
    {
        Flux<String> stringFlux = Flux.fromIterable(names);
        StepVerifier.create(stringFlux.log()).expectNext("Adam","Anna","Jack","Jenny").verifyComplete();

    }

    @Test
    public void flexUsingArray()
    {
        String[]names = new String[]{"Adam","Anna","Jack","Jenny"};
        Flux<String> stringFlux = Flux.fromArray(names);
        StepVerifier.create(stringFlux.log()).expectNext("Adam","Anna","Jack","Jenny").verifyComplete();
    }

    @Test
    public void fluxUsingStream()
    {
        Flux<String> stringFlux = Flux.fromStream(names.stream());
        StepVerifier.create(stringFlux.log()).expectNext("Adam","Anna","Jack","Jenny").verifyComplete();
    }

    @Test
    public void monoUsingJustOrEmpty()
    {
        Mono<Object> objectMono = Mono.justOrEmpty(null);
        StepVerifier.create(objectMono.log()).verifyComplete();
    }

    @Test
    public void monoUsingSupplier()
    {
        Supplier<String> stringSupplier = () -> "adam";
        Mono<String> stringMono = Mono.fromSupplier(stringSupplier);
        StepVerifier.create(stringMono.log()).expectNext("adam").verifyComplete();
    }

    @Test
    public void fluxUsingRange()
    {
        Flux<Integer> range = Flux.range(1, 5);
        StepVerifier.create(range.log()).expectNext(1,2,3,4,5).verifyComplete();
    }
}
