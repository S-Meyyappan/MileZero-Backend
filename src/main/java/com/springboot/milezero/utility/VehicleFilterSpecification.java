package com.springboot.milezero.utility;

import com.springboot.milezero.model.Vehicle;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class VehicleFilterSpecification {

    public static Specification<Vehicle> getFilteredVehicles(Long branchId,
                                                             String search,
                                                             String brand,
                                                             String fuelType,
                                                             String transmission,
                                                             Integer seats,
                                                             String category) {
        return new Specification<Vehicle>() {
            @Override
            public Predicate toPredicate(Root<Vehicle> vehicleRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                // 2. Search Logic

                // 2.1 Search by brand or model
                if (search != null && !search.trim().isEmpty()) {
                    String lowerCaseSearch = "%" + search.toLowerCase() + "%";
                    Predicate brandLike = cb.like(cb.lower(vehicleRoot.get("manufacturer")), lowerCaseSearch);
                    Predicate modelLike = cb.like(cb.lower(vehicleRoot.get("model")), lowerCaseSearch);
                    // Match brand or model
                    predicates.add(cb.or(brandLike, modelLike));
                }

                // 2.2 Filter by brand
                if (brand != null && !brand.trim().isEmpty()) {
                    String lowerCaseBrand = brand.toLowerCase();
                    predicates.add(cb.equal(cb.lower(vehicleRoot.get("manufacturer")), lowerCaseBrand));
                }

                // 2.3 Filter By FuelType
                if (fuelType != null && !fuelType.trim().isEmpty()) {
                    String lowerCaseFuelType = fuelType.toLowerCase();
                    predicates.add(cb.equal(cb.lower(vehicleRoot.get("fuelType")), lowerCaseFuelType));
                }

                // 2.4 Filter by transmission
                if (transmission != null && !transmission.trim().isEmpty()) {
                    String lowerCaseTransmission = transmission.toLowerCase();
                    predicates.add(cb.equal(cb.lower(vehicleRoot.get("transmission")), lowerCaseTransmission));
                }

                // 2.5 Filter by seats
                if (seats != null) {
                    predicates.add(cb.equal(vehicleRoot.get("seatCount"), seats));
                }

                // 2.6 Filter by category
                if (category != null && !category.trim().isEmpty()) {
                    String lowerCaseCategory = category.toLowerCase();
                    predicates.add(cb.equal(cb.lower(vehicleRoot.get("category").get("name")), lowerCaseCategory));
                }

                // 2.7 Filter by branch
                if (branchId != null) {
                    predicates.add(cb.equal(vehicleRoot.get("branch").get("id"), branchId));
                }


                // 3. Return the combined predicate
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
