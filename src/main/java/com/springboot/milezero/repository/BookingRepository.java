package com.springboot.milezero.repository;

import com.springboot.milezero.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
           SELECT b FROM Booking b where b.pickupBranch.id = :id
           """)
    List<Booking> findByBranchId(long id);

    @Query("""
           SELECT b FROM Booking b where b.vehicle.id = :id
           """)
    List<Booking> findByVehicleId(long id);

    @Query("""
           SELECT b FROM Booking b where b.customer.name = :customerName
           """)
    List<Booking> findByCustomerName(String customerName);
}
