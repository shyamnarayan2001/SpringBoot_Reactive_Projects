package com.narayan.reactive.reactivemongosample.resource;

import com.narayan.reactive.reactivemongosample.model.Employee;
import com.narayan.reactive.reactivemongosample.model.EmployeeEvent;
import com.narayan.reactive.reactivemongosample.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@RestController
@RequestMapping("/rest/employee")
@RequiredArgsConstructor
public class EmployeeResource {

    private final EmployeeRepository employeeRepository;

    @GetMapping("/all")
    public Flux<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Employee> getId(@PathVariable("id") final String id) {
        return employeeRepository.findById(id);
    }

    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EmployeeEvent> getEvents(@PathVariable("id") final String empId) {
        return employeeRepository.findById(empId)
                .flatMapMany(employee -> {
                    Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(2));
                    Flux<EmployeeEvent> employeeEventFlux = Flux.fromStream(Stream.generate(()-> new EmployeeEvent(employee, new Date())));
                    //zipping two streams - interval and employeeEvent
                    return Flux.zip(intervalFlux, employeeEventFlux)
                            .map(Tuple2::getT2);
                });
    }
}
