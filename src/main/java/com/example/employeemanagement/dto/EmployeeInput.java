package com.example.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInput {
    private String name;
    private Integer age;
    private String department;
    private List<String> subjects;
    private Double attendance;
    private String email;
    private String phone;
    private String address;
    private String joiningDate;
    private Double salary;
    private String position;
    private String status;
    
    public LocalDate getJoiningDateAsLocalDate() {
        return joiningDate != null ? LocalDate.parse(joiningDate) : null;
    }
}
