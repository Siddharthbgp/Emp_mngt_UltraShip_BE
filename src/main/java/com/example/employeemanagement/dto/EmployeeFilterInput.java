package com.example.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFilterInput {
    private String name;
    private String department;
    private String status;
    private Integer minAge;
    private Integer maxAge;
}
