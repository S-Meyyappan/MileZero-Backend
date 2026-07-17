package com.springboot.milezero.repository;

import com.springboot.milezero.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("""
          SELECT v FROM Vehicle v WHERE v.category.id = :id
          """)
    Optional<Vehicle> findByCategoryId(long id);
}