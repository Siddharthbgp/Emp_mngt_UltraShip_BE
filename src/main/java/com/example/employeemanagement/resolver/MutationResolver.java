package com.example.employeemanagement.resolver;

import com.example.employeemanagement.dto.AuthPayload;
import com.example.employeemanagement.dto.EmployeeInput;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.security.JwtUtil;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MutationResolver {
    
    private final EmployeeService employeeService;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    
    @MutationMapping
    public AuthPayload login(@Argument String username, @Argument String password) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
        
        if (!userService.verifyPassword(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        
        String token = jwtUtil.generateToken(username, user.getRole().name());
        
        return new AuthPayload(token, user);
    }
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Employee addEmployee(@Argument EmployeeInput input) {
        Employee employee = new Employee();
        employee.setName(input.getName());
        employee.setAge(input.getAge());
        employee.setDepartment(input.getDepartment());
        employee.setSubjects(input.getSubjects());
        employee.setAttendance(input.getAttendance());
        employee.setEmail(input.getEmail());
        employee.setPhone(input.getPhone());
        employee.setAddress(input.getAddress());
        employee.setJoiningDate(input.getJoiningDateAsLocalDate());
        employee.setSalary(input.getSalary());
        employee.setPosition(input.getPosition());
        employee.setStatus(input.getStatus() != null ? input.getStatus() : "ACTIVE");
        
        return employeeService.save(employee);
    }
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Employee updateEmployee(@Argument Long id, @Argument EmployeeInput input) {
        Employee employee = new Employee();
        employee.setName(input.getName());
        employee.setAge(input.getAge());
        employee.setDepartment(input.getDepartment());
        employee.setSubjects(input.getSubjects());
        employee.setAttendance(input.getAttendance());
        employee.setEmail(input.getEmail());
        employee.setPhone(input.getPhone());
        employee.setAddress(input.getAddress());
        employee.setJoiningDate(input.getJoiningDateAsLocalDate());
        employee.setSalary(input.getSalary());
        employee.setPosition(input.getPosition());
        employee.setStatus(input.getStatus());
        
        return employeeService.update(id, employee);
    }
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteEmployee(@Argument Long id) {
        return employeeService.delete(id);
    }
}
