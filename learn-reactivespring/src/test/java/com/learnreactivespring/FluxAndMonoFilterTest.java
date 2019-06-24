package com.learnreactivespring;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {

    List<String> names = Arrays.asList("Tom", "Anna", "Jack", "Jill");

    @Test
    public void filterTest(){
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(s -> s.startsWith("J"))
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Jack", "Jill" )
                .verifyComplete();

    }

    @Test
    public void filterTestLength(){
        Flux<String> namesFlux = Flux.fromIterable(names) //"Tom", "Anna", "Jack", "Jill"
                .filter(s -> s.length() > 3)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Anna", "Jack", "Jill" )
                .verifyComplete();

    }
}
