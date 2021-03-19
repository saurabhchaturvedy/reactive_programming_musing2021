package com.learn.reactive.learnreactivespring.fluxandmonoplayground;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

public class FluxAndMonoTransformTest {


    List<String> names = Arrays.asList("Adam", "Anna", "Jack", "Jenny");

    @Test
    public void transformUsingMap() {
        Flux<String> namesFlux = Flux.fromIterable(names).map(s -> s.toUpperCase()).log();
        StepVerifier.create(namesFlux).expectNext("ADAM", "ANNA", "JACK", "JENNY").verifyComplete();
    }


    @Test
    public void transformUsingMap_Length() {
        Flux<Integer> namesFlux = Flux.fromIterable(names).map(s -> s.length()).log();
        StepVerifier.create(namesFlux).expectNext(4, 4, 4, 5).verifyComplete();
    }

    @Test
    public void transformUsingMap_Repeat() {
        Flux<Integer> namesFlux = Flux.fromIterable(names).map(s -> s.length()).repeat(1).log();
        StepVerifier.create(namesFlux).expectNext(4, 4, 4, 5, 4, 4, 4, 5).verifyComplete();
    }

    @Test
    public void transformUsingMap_Filter() {
        Flux<Integer> namesFlux = Flux.fromIterable(names).filter(s -> s.startsWith("J")).map(s -> s.length()).repeat(1).log();
        StepVerifier.create(namesFlux).expectNext(4, 5, 4, 5).verifyComplete();
    }

    @Test
    public void transformUsingFlatMap()
    {
        Flux<String> flatMapFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F")).flatMap(s -> {
            return Flux.fromIterable(convertToList(s));
        }).log();

        StepVerifier.create(flatMapFlux).expectNextCount(12).verifyComplete();
    }

    @SneakyThrows
    private List<String> convertToList(String s)  {
        Thread.sleep(1000);
        return Arrays.asList(s,"newValue");
    }

    @Test
    public void transformUsingFlatMap_usingParallel()
    {
        Flux<String> flatMapFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F")).window(2).flatMap((s) ->
            s.map(this::convertToList).subscribeOn(parallel()).flatMap(st->Flux.fromIterable(st))
        ).log();

        StepVerifier.create(flatMapFlux).expectNextCount(12).verifyComplete();
    }

    @Test
    public void transformUsingFlatMap_usingParallel_MaintainOrder()
    {
        Flux<String> flatMapFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F")).window(2).flatMapSequential((s) ->
                s.map(this::convertToList).subscribeOn(parallel()).flatMap(st->Flux.fromIterable(st))
        ).log();


        // concatMap maintains order

        // flatMapSequential maintains order and faster execution parallel

        StepVerifier.create(flatMapFlux).expectNextCount(12).verifyComplete();
    }
}
