package com.example.employeemanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(name = "department", nullable = false)
    private String department; // Mapped from "class"

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_subjects", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "subject")
    private List<String> subjects;

    @Column(nullable = false)
    private Double attendance; // Percentage (0-100)

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String phone;

    @Column
    private String address;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column
    private Double salary;

    @Column
    private String position;

    @Column
    private String status; // ACTIVE, INACTIVE, ON_LEAVE
}
