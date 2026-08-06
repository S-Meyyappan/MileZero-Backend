package com.springboot.milezero.repository;

import com.springboot.milezero.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("""
           SELECT e FROM Employee e WHERE e.id = :id AND e.user.isActive = true
           """)
    Optional<Employee> fetchById(long id);

    @Query("""
           SELECT e FROM Employee e WHERE e.user.isActive = true
           """)
    List<Employee> fetchAll();

    @Query("""
            SELECT e FROM Employee e WHERE e.branch.id = :branchId AND e.user.role = MANAGER
            """)
    Optional<Employee> hasManager(long branchId);

    @Query("""
            SELECT e FROM Employee e WHERE e.user.email = :email AND e.user.isActive = true
            """)
    Optional<Employee> fetchByEmail(String email);
}
