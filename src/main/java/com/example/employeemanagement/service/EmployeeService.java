package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    
    @Transactional(readOnly = true)
    public Page<Employee> findAll(String name, String department, String status, 
                                   Integer minAge, Integer maxAge,
                                   int page, int limit, String sortField, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, limit, sort);
        
        return employeeRepository.findByFilters(name, department, status, minAge, maxAge, pageable);
    }
    
    @Transactional(readOnly = true)
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Employee> findByIds(List<Long> ids) {
        return employeeRepository.findByIdIn(ids);
    }
    
    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    @Transactional
    public Employee update(Long id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        
        employee.setName(updatedEmployee.getName());
        employee.setAge(updatedEmployee.getAge());
        employee.setDepartment(updatedEmployee.getDepartment());
        employee.setSubjects(updatedEmployee.getSubjects());
        employee.setAttendance(updatedEmployee.getAttendance());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setPhone(updatedEmployee.getPhone());
        employee.setAddress(updatedEmployee.getAddress());
        employee.setJoiningDate(updatedEmployee.getJoiningDate());
        employee.setSalary(updatedEmployee.getSalary());
        employee.setPosition(updatedEmployee.getPosition());
        employee.setStatus(updatedEmployee.getStatus());
        
        return employeeRepository.save(employee);
    }
    
    @Transactional
    public boolean delete(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
