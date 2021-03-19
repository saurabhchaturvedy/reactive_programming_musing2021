package com.learn.reactive.learnreactivespring.fluxandmonoplayground;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ColdAndHotPublisherTest {


    @Test
    @SneakyThrows
    public void publisherTest_COLD()
    {
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F").delayElements(Duration.ofSeconds(1));

        stringFlux.subscribe((element)->System.out.println("subscriber 1"+element));

        Thread.sleep(2000);

        stringFlux.subscribe((element)->System.out.println("subscriber 2"+element));

        // every other new subscriber emits value from beginning

        Thread.sleep(4000);
    }


    @Test
    @SneakyThrows
    public void publisherTest_HOT()
    {
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F").delayElements(Duration.ofSeconds(1));

        ConnectableFlux<String> connectableFlux = stringFlux.publish();
        connectableFlux.connect();

        connectableFlux.subscribe((element)->System.out.println("subscriber 1"+element));

        Thread.sleep(3000);

        connectableFlux.subscribe((element)->System.out.println("subscriber 2"+element));

        // every other new subscriber emits values from the time its subscribed to and not from beginning

        Thread.sleep(4000);
    }
}
