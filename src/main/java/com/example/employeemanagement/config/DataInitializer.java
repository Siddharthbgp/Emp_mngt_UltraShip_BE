package com.example.employeemanagement.config;

import com.example.employeemanagement.model.User;
import com.example.employeemanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final UserService userService;
    
    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if not exists
        try {
            userService.createUser(
                    "admin",
                    "admin123",
                    User.Role.ADMIN,
                    "admin@example.com",
                    "Administrator"
            );
            System.out.println("Default admin user created: username=admin, password=admin123");
        } catch (RuntimeException e) {
            System.out.println("Admin user already exists");
        }
        
        // Create default employee user if not exists
        try {
            userService.createUser(
                    "employee",
                    "employee123",
                    User.Role.EMPLOYEE,
                    "employee@example.com",
                    "Employee User"
            );
            System.out.println("Default employee user created: username=employee, password=employee123");
        } catch (RuntimeException e) {
            System.out.println("Employee user already exists");
        }
    }
}
