package com.example.employeemanagement.resolver;

import com.example.employeemanagement.dto.EmployeePage;
import com.example.employeemanagement.dto.PaginationInput;
import com.example.employeemanagement.dto.SortInput;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueryResolverTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private QueryResolver queryResolver;

    @Test
    void testListEmployees() {
        // Arrange
        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setName("John Doe");

        Employee emp2 = new Employee();
        emp2.setId(2L);
        emp2.setName("Jane Doe");

        List<Employee> employees = Arrays.asList(emp1, emp2);
        Page<Employee> page = new PageImpl<>(employees);

        when(employeeService.findAll(
                isNull(), isNull(), isNull(), isNull(), isNull(),
                anyInt(), anyInt(), anyString(), anyString())).thenReturn(page);

        PaginationInput pagination = new PaginationInput(0, 10);
        SortInput sort = new SortInput("id", SortInput.SortDirection.ASC);

        // Act
        EmployeePage result = queryResolver.listEmployees(null, pagination, sort);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalElements());
        verify(employeeService, times(1)).findAll(
                isNull(), isNull(), isNull(), isNull(), isNull(),
                anyInt(), anyInt(), anyString(), anyString());
    }

    @Test
    void testGetEmployee() {
        // Arrange
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setName("John Doe");

        when(employeeService.findById(employeeId)).thenReturn(Optional.of(employee));

        // Act
        Employee result = queryResolver.getEmployee(employeeId);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(employeeService, times(1)).findById(employeeId);
    }

    @Test
    void testGetEmployeeNotFound() {
        // Arrange
        Long employeeId = 999L;
        when(employeeService.findById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            queryResolver.getEmployee(employeeId);
        });

        verify(employeeService, times(1)).findById(employeeId);
    }
}
