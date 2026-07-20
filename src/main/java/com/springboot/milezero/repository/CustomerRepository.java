package com.springboot.milezero.repository;

import com.springboot.milezero.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("""
           SELECT c FROM Customer c WHERE c.id = :id AND c.user.isActive = true
           """)
    Optional<Customer> fetchById(long id);

    @Query("""
           SELECT c FROM Customer c WHERE c.user.isActive = true
           """)
    List<Customer> fetchAll();
}
