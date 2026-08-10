package com.springboot.milezero.utility;

import com.springboot.milezero.model.Vehicle;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class VehicleFilterSpecification {
    private VehicleFilterSpecification() {
        /* This utility class should not be instantiated */
    }

    public static Specification<Vehicle> getFilteredVehicles(Long branchId,
                                                             String search,
                                                             String brand,
                                                             String fuelType,
                                                             String transmission,
                                                             Integer seats,
                                                             String currentStatus,
                                                             String category) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 2. Search Logic
            // 2.1 Search by brand or model
            addSearchPredicate(predicates, root, cb, search);
            // 2.2 Filter by brand
            addStringFilter(predicates, root, cb, "manufacturer", brand);
            // 2.3 Filter By FuelType
            addStringFilter(predicates, root, cb, "fuelType", fuelType);
            // 2.4 Filter by transmission
            addStringFilter(predicates, root, cb, "transmission", transmission);
            // 2.5 Filter by seats
            addSeatsFilter(predicates, root, cb, seats);
            // 2.6 Filter by category
            addCategoryFilter(predicates, root, cb, category);
            // 2.7 Filter by branch
            addBranchFilter(predicates, root, cb, branchId);
            // 2.8 Filter by current status
            addStringFilter(predicates, root, cb, "currentStatus", currentStatus);
            // 3. Return the combined predicate
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addSearchPredicate(List<Predicate> predicates, Root<?> root, CriteriaBuilder cb, String search) {
        if (isNotBlank(search)) {
            String value = "%" + search.toLowerCase() + "%";
            Predicate brandLike = cb.like(cb.lower(root.get("manufacturer")), value);
            Predicate modelLike = cb.like(cb.lower(root.get("model")), value);
            // Match brand or model
            predicates.add(cb.or(brandLike, modelLike));
        }
    }

    private static void addStringFilter(List<Predicate> predicates, Root<?> root, CriteriaBuilder cb, String field, String value) {
        if (isNotBlank(value)) {
            predicates.add(cb.equal(cb.lower(root.get(field)), value.toLowerCase()));
        }
    }

    private static void addSeatsFilter(List<Predicate> predicates, Root<?> root, CriteriaBuilder cb, Integer seats) {
        if (seats != null) {
            predicates.add(cb.equal(root.get("seatCount"), seats));
        }
    }

    private static void addCategoryFilter(List<Predicate> predicates, Root<?> root, CriteriaBuilder cb, String category) {
        if (isNotBlank(category)) {
            predicates.add(cb.equal(cb.lower(root.get("category").get("name")), category.toLowerCase()));
        }
    }

    private static void addBranchFilter(List<Predicate> predicates, Root<?> root, CriteriaBuilder cb, Long branchId) {
        if (branchId != null) {
            predicates.add(cb.equal(root.get("branch").get("id"), branchId));
        }
    }

    private static boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
