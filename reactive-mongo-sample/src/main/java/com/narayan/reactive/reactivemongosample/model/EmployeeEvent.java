package com.narayan.reactive.reactivemongosample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeEvent {
    private Employee employee;
    private Date date;
}
