package com.example.employeemanagement.resolver;

import com.example.employeemanagement.dto.EmployeeInput;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutationResolverTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private MutationResolver mutationResolver;

    @Test
    void testAddEmployee() {
        // Arrange
        EmployeeInput input = new EmployeeInput();
        input.setName("John Doe");
        input.setAge(30);
        input.setDepartment("Engineering");
        input.setSubjects(Arrays.asList("Java", "Spring"));
        input.setAttendance(95.0);
        input.setEmail("john@example.com");

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName("John Doe");

        when(employeeService.save(any(Employee.class))).thenReturn(expectedEmployee);

        // Act
        Employee result = mutationResolver.addEmployee(input);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(employeeService, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee() {
        // Arrange
        Long employeeId = 1L;
        EmployeeInput input = new EmployeeInput();
        input.setName("Jane Doe");
        input.setAge(31);
        input.setDepartment("Marketing");
        input.setSubjects(Arrays.asList("Marketing", "SEO"));
        input.setAttendance(92.0);
        input.setEmail("jane@example.com");

        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(employeeId);
        expectedEmployee.setName("Jane Doe");

        when(employeeService.update(anyLong(), any(Employee.class))).thenReturn(expectedEmployee);

        // Act
        Employee result = mutationResolver.updateEmployee(employeeId, input);

        // Assert
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        verify(employeeService, times(1)).update(eq(employeeId), any(Employee.class));
    }

    @Test
    void testDeleteEmployee() {
        // Arrange
        Long employeeId = 1L;
        when(employeeService.delete(employeeId)).thenReturn(true);

        // Act
        Boolean result = mutationResolver.deleteEmployee(employeeId);

        // Assert
        assertTrue(result);
        verify(employeeService, times(1)).delete(employeeId);
    }
}
