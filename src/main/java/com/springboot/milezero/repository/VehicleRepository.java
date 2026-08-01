package com.springboot.milezero.repository;

import com.springboot.milezero.dto.response.CategoryAvailabilityRespDto;
import com.springboot.milezero.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("""
          SELECT v FROM Vehicle v WHERE v.category.id = :id
          """)
    Optional<Vehicle> findByCategoryId(long id);

    @Query("""
            SELECT c.id AS categoryId, c.name AS categoryName,
                   COUNT(DISTINCT v.id) AS totalVehicles,
                   (SELECT COUNT(b.id) FROM Booking b
                   WHERE b.category.id = c.id
                   AND b.plannedPickup < :chosenReturn
                   AND b.plannedReturn > :chosenPickup) AS bookedVehicles,
                   (COUNT(DISTINCT v.id) -
                   (SELECT COUNT(b2.id) FROM Booking b2
                   WHERE b2.category.id = c.id
                       AND b2.plannedPickup < :chosenReturn
                       AND b2.plannedReturn > :chosenPickup)) AS vehiclesAvailableToBook
                   FROM Vehicle v
                   RIGHT JOIN v.category c
                   GROUP BY c.id, c.name
            """)
    List<CategoryAvailabilityRespDto> getAllAvailableVehicles(LocalDateTime chosenPickup,
                                                              LocalDateTime chosenReturn);

    @Query("""
            SELECT c.id AS categoryId, c.name AS categoryName,
                   COUNT(DISTINCT v.id) AS totalVehicles,
                   (SELECT COUNT(b.id) FROM Booking b
                   WHERE b.category.id = c.id
                   AND b.plannedPickup < :chosenReturn
                   AND b.plannedReturn > :chosenPickup
                   AND b.pickupBranch.id = :branchId ) AS bookedVehicles,
                   (COUNT(DISTINCT v.id) -
                   (SELECT COUNT(b2.id) FROM Booking b2
                   WHERE b2.category.id = c.id
                       AND b2.plannedPickup < :chosenReturn
                       AND b2.plannedReturn > :chosenPickup
                       AND b2.pickupBranch.id = :branchId )) AS vehiclesAvailableToBook
                   FROM Vehicle v
                   RIGHT JOIN v.category c ON v.branch.id = :branchId
                   GROUP BY c.id, c.name
            """)
    List<CategoryAvailabilityRespDto> getAllAvailableVehiclesByBranch(LocalDateTime chosenPickup,
                                                                      LocalDateTime chosenReturn,
                                                                      Long branchId);

    @Query("""
            SELECT v FROM Vehicle v WHERE v.category.id = :id
            """)
    List<Vehicle> getAllByCategory(long id);

    @Query("""
            SELECT v FROM Vehicle v WHERE v.branch.id = :id
            """)
    List<Vehicle> getAllByBranch(long id);

    @Query("""
            SELECT v FROM Vehicle v WHERE v.id NOT IN (
                SELECT b.vehicle.id FROM Booking b
                WHERE b.pickupBranch.id = :branchId
                AND b.plannedPickup < :chosenReturn
                AND b.plannedReturn > :chosenPickup
            )
            """)
    List<Vehicle> getVehicleByAvailability(LocalDateTime chosenPickup,
                                           LocalDateTime chosenReturn,
                                           Long branchId);
}