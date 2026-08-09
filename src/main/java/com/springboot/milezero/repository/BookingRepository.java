package com.springboot.milezero.repository;

import com.springboot.milezero.dto.response.BookingStatsDto;
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

    @Query("""
            SELECT
            COUNT(b.id) as total,
            SUM(CASE WHEN b.bookingStatus = 'BOOKED' THEN 1 ELSE 0 END) as booked,
            SUM(CASE WHEN b.bookingStatus = 'ACTIVE' THEN 1 ELSE 0 END) as activeTrips,
            SUM(CASE WHEN b.bookingStatus = 'COMPLETED' THEN 1 ELSE 0 END) as completed,
            SUM(CASE WHEN b.bookingStatus = 'CANCELLED' THEN 1 ELSE 0 END) as cancelled,
            SUM(CASE WHEN b.bookingStatus = 'RETURNED' THEN 1 ELSE 0 END) as returned,
            SUM(CASE WHEN b.bookingStatus = 'NO_SHOW' THEN 1 ELSE 0 END) as missed
            FROM Booking b
            WHERE b.customer.id = :id
            """)
    BookingStatsDto fetchCustomerBookingStats(long id);

    @Query("""
            SELECT b FROM Booking b
            where b.customer.id = :id
            and b.bookingStatus = 'BOOKED'
            and b.plannedPickup >= :today
            ORDER BY b.plannedPickup ASC
            """)
    List<Booking> fetchUpcomingCustomerBooking(long id, Instant today, Pageable pageable);

    @Query("""
            SELECT b FROM Booking b
            where b.customer.id = :id
            and b.plannedPickup <= :today
            ORDER BY b.plannedPickup DESC
            """)
    List<Booking> fetchRecentCustomerBookings(long id, Instant today, Pageable pageable);

    @Query("""
            SELECT
            COUNT(b.id) as total,
            SUM(CASE WHEN b.bookingStatus = 'BOOKED' THEN 1 ELSE 0 END) as booked,
            SUM(CASE WHEN b.bookingStatus = 'ACTIVE' THEN 1 ELSE 0 END) as activeTrips,
            SUM(CASE WHEN b.bookingStatus = 'COMPLETED' THEN 1 ELSE 0 END) as completed,
            SUM(CASE WHEN b.bookingStatus = 'CANCELLED' THEN 1 ELSE 0 END) as cancelled,
            SUM(CASE WHEN b.bookingStatus = 'RETURNED' THEN 1 ELSE 0 END) as returned,
            SUM(CASE WHEN b.bookingStatus = 'NO_SHOW' THEN 1 ELSE 0 END) as missed
            FROM Booking b
            WHERE b.pickupBranch.id = :id
            """)
    BookingStatsDto fetchEmployeeBookingStats(long id);

    @Query("""
            SELECT b FROM Booking b
            where b.pickupBranch.id = :id
            and b.bookingStatus = 'BOOKED'
            and b.plannedPickup >= :today
            ORDER BY b.plannedPickup ASC
            """)
    List<Booking> fetchEmployeeUpcomingPickups(long id, Instant today, Pageable pageable);

    @Query("""
            SELECT b FROM Booking b
            where b.returnBranch.id = :id
            and b.bookingStatus = 'BOOKED'
            and b.plannedReturn >= :today
            ORDER BY b.plannedReturn ASC
            """)
    List<Booking> fetchEmployeeUpcomingReturns(long id, Instant today, Pageable pageable);

    @Query("""
            SELECT b FROM Booking b
            where b.pickupBranch.id = :id
            and b.plannedPickup <= :today
            ORDER BY b.plannedPickup DESC
            """)
    List<Booking> fetchEmployeeRecentBookings(long id, Instant today, Pageable pageable);
}
