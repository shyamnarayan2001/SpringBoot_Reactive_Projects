package com.narayan.reactive.reactivemongosample;

import com.narayan.reactive.reactivemongosample.model.Employee;
import com.narayan.reactive.reactivemongosample.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ReactiveMongoSampleApplication {

	@Bean
	CommandLineRunner employees (EmployeeRepository employeeRepository) {
		return args -> {
			employeeRepository
					.deleteAll()
					.subscribe(null, null, () -> {
						Stream.of(
								new Employee(UUID.randomUUID().toString(), "Raghu", 25000L),
								new Employee(UUID.randomUUID().toString(), "Praveen", 30000L),
								new Employee(UUID.randomUUID().toString(), "Shyam", 35000L),
								new Employee(UUID.randomUUID().toString(), "Aditya", 40000L)
						).forEach(employee -> employeeRepository
								.save(employee)
								.subscribe(System.out::println));
					});
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ReactiveMongoSampleApplication.class, args);
	}

}
