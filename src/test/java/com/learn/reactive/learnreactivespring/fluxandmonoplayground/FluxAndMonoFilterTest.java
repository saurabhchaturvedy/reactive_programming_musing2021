package com.learn.reactive.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {

    List<String> names = Arrays.asList("Adam","Anna","Jack","Jenny");


    @Test
    public void filterTest()
    {
        Flux<String> nameMono = Flux.fromIterable(names).filter(s -> s.startsWith("J")).filter(s->s.length()>4).log();
        StepVerifier.create(nameMono).expectNext("Jenny").verifyComplete();
    }
}
