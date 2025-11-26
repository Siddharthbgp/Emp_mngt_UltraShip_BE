package com.example.employeemanagement.resolver;

import com.example.employeemanagement.dto.*;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class QueryResolver {
    
    private final EmployeeService employeeService;
    private final UserService userService;
    
    @QueryMapping
    public EmployeePage listEmployees(
            @Argument EmployeeFilterInput filter,
            @Argument PaginationInput pagination,
            @Argument SortInput sort) {
        
        if (pagination == null) {
            pagination = new PaginationInput(0, 10);
        }
        if (sort == null) {
            sort = new SortInput("id", SortInput.SortDirection.ASC);
        }
        
        String name = filter != null ? filter.getName() : null;
        String department = filter != null ? filter.getDepartment() : null;
        String status = filter != null ? filter.getStatus() : null;
        Integer minAge = filter != null ? filter.getMinAge() : null;
        Integer maxAge = filter != null ? filter.getMaxAge() : null;
        
        Page<Employee> page = employeeService.findAll(
                name, department, status, minAge, maxAge,
                pagination.getPage(), pagination.getLimit(),
                sort.getField(), sort.getDirection().name()
        );
        
        EmployeePage employeePage = new EmployeePage();
        employeePage.setContent(page.getContent());
        employeePage.setTotalElements((int) page.getTotalElements());
        employeePage.setTotalPages(page.getTotalPages());
        employeePage.setCurrentPage(page.getNumber());
        employeePage.setPageSize(page.getSize());
        
        return employeePage;
    }
    
    @QueryMapping
    public Employee getEmployee(@Argument Long id) {
        return employeeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }
    
    @QueryMapping
    public User me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        
        String username = authentication.getName();
        return userService.findByUsername(username)
                .orElse(null);
    }
}
