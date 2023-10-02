package com.example.escalaCidada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.escalaCidada.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT COUNT(e) FROM Employee e")
    Long countTotalEmployees();
}
