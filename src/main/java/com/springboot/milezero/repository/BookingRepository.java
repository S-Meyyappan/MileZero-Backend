package com.springboot.milezero.repository;

import com.springboot.milezero.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    @Query("""
           SELECT b FROM Booking b where b.pickupBranch.id = :id
           """)
    List<Booking> findByBranchId(long id);

    @Query("""
           SELECT b FROM Booking b where b.vehicle.id = :id
           """)
    List<Booking> findByVehicleId(long id);

    @Query("""
           SELECT b FROM Booking b where b.customer.user.email = :email
           """)
    Page<Booking> findByCustomerEmail(String email, Specification<Booking> spec, Pageable pageable);

    @Query("""
            SELECT b FROM Booking b
            where b.vehicle.id = :id
            and (b.plannedPickup < :chosenReturn and b.plannedReturn > :chosenPickup)
            """)
    List<Booking> checkOverlap(long id,  Instant chosenPickup,  Instant chosenReturn);
}
