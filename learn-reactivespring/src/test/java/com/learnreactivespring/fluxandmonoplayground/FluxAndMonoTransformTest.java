package com.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoTransformTest {

    List<String> names = Arrays.asList("Tom", "Anna", "Jack", "Jill");

    @Test
    public void transformUsingMap(){
        Flux<String> namesFlux = Flux.fromIterable(names)
                .map(s -> s.toUpperCase())
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("TOM", "ANNA", "JACK", "JILL" )
                .verifyComplete();

    }

    @Test
    public void transformUsingMap_Length(){
        Flux<Integer>  intFlux = Flux.fromIterable(names)
                .map(s -> s.length())
                .log();

        StepVerifier.create(intFlux)
                .expectNext(3, 4, 4, 4)
                .verifyComplete();

    }

    @Test
    public void transformUsingMap_Length_Repeat(){
        Flux<Integer>  intFlux = Flux.fromIterable(names)
                .map(s -> s.length())
                .repeat(1)
                .log();

        StepVerifier.create(intFlux)
                .expectNext(3, 4, 4, 4,3, 4, 4, 4)
                .verifyComplete();

    }

    @Test
    public void transformUsingMap_Filter(){
        Flux<String>  stringFlux = Flux.fromIterable(names)
                .filter(s -> s.length()>3)
                .map(s -> s.toUpperCase())
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("ANNA", "JACK", "JILL")
                .verifyComplete();

    }

    @Test
    //Takes 6 seconds to complete (no parallel threads)
    public void transformUsingFlatMap(){
        Flux<String>  stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F")) //A, B, C, D, E, F
                .flatMap(s -> {
                    return Flux.fromIterable(convertToList(s));
                }) //db or external service call that returns a Flux (Flux<String> here)
                .log();

        // Takes 6 seconds to run because of the Thread.Sleep of 1 second
        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

    }

    private List<String> convertToList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s, "newValue");
    }

    @Test
    //Takes only 2 seconds are they are executed in parallel, but the results will not be in sequence
    public void transformUsingFlatMap_usingParallel(){
        Flux<String>  stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F")) //Flux<String>
                .window(2) //Flux<Flux<String>> (A,B), (C,D), (E,F)
                .flatMap((s) -> s.map(this::convertToList).subscribeOn(Schedulers.parallel())) //Flux<List<String>>
                .flatMap(s -> Flux.fromIterable(s)) //Flux<String>
                .log();

        // Takes 6 seconds to run because of the Thread.Sleep of 1 second
        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

    }

    @Test
    //Takes only 2 seconds are they are executed in parallel and the results will be in sequence
    public void transformUsingFlatMap_parallel_maintain_order(){
        Flux<String>  stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F")) //Flux<String>
                .window(2) //Flux<Flux<String>> (A,B), (C,D), (E,F)
                .flatMapSequential((s) -> s.map(this::convertToList).subscribeOn(Schedulers.parallel())) //Flux<List<String>>
                .flatMap(s -> Flux.fromIterable(s)) //Flux<String>
                .log();

        // Takes 6 seconds to run because of the Thread.Sleep of 1 second
        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

    }


}
