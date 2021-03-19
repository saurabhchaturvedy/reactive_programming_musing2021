package com.learn.reactive.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoCombineTest {


    @Test
    public void combineUsingMerge()
    {
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");

        Flux<String> merge = Flux.merge(flux1, flux2);
        StepVerifier.create(merge.log()).expectSubscription().expectNext("A", "B", "C","D", "E", "F").verifyComplete();
    }


    @Test
    public void combineUsingMerge_Delay()
    {
        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));

        Flux<String> merge = Flux.concat(flux1, flux2);   // flux in an interleaved fashion if merge is used , using concat resolves issue
        StepVerifier.
                create(merge.log()).
                expectSubscription().
                expectNext("A", "B", "C","D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void combineUsingMerge_Zip()
    {
        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));

        Flux<String> merge = Flux.zip(flux1, flux2,(t1,t2)-> t1.concat(t2));   // zip helps picking up corresponding elements to concatenate streams simultaneously
        StepVerifier.
                create(merge.log()).
                expectSubscription().
                expectNext("AD","BE", "CF")
                .verifyComplete();
    }
}
