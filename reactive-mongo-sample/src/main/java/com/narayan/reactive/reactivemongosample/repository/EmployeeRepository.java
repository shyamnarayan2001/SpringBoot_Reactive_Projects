package com.narayan.reactive.reactivemongosample.repository;

import com.narayan.reactive.reactivemongosample.model.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {

}
