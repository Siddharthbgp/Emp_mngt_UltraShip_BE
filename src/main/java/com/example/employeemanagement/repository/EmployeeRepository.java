package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find by department
    Page<Employee> findByDepartment(String department, Pageable pageable);

    // Find by name containing (case-insensitive)
    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Find by status
    Page<Employee> findByStatus(String status, Pageable pageable);

    // Custom query for filtering - using native SQL to avoid type inference issues
    @Query(value = "SELECT * FROM employees e WHERE " +
            "(:name IS NULL OR LOWER(CAST(e.name AS TEXT)) LIKE LOWER(CONCAT('%', CAST(:name AS TEXT), '%'))) AND " +
            "(:department IS NULL OR e.department = :department) AND " +
            "(:status IS NULL OR e.status = :status) AND " +
            "(:minAge IS NULL OR e.age >= :minAge) AND " +
            "(:maxAge IS NULL OR e.age <= :maxAge)", countQuery = "SELECT COUNT(*) FROM employees e WHERE " +
            "(:name IS NULL OR LOWER(CAST(e.name AS TEXT)) LIKE LOWER(CONCAT('%', CAST(:name AS TEXT), '%'))) AND "
            +
            "(:department IS NULL OR e.department = :department) AND " +
            "(:status IS NULL OR e.status = :status) AND " +
            "(:minAge IS NULL OR e.age >= :minAge) AND " +
            "(:maxAge IS NULL OR e.age <= :maxAge)", nativeQuery = true)
    Page<Employee> findByFilters(
            @Param("name") String name,
            @Param("department") String department,
            @Param("status") String status,
            @Param("minAge") Integer minAge,
            @Param("maxAge") Integer maxAge,
            Pageable pageable);

    // Find employees by IDs (for DataLoader batching)
    @Query("SELECT e FROM Employee e WHERE e.id IN :ids")
    List<Employee> findByIdIn(@Param("ids") List<Long> ids);
}
