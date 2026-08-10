package com.springboot.milezero.utility;

import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.BookingType;
import com.springboot.milezero.model.Booking;
import com.springboot.milezero.model.Pickuplog;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PickuplogFilterSpecification {
    private PickuplogFilterSpecification() {
        /* This utility class should not be instantiated */
    }


    public static Specification<Pickuplog> getFilterSpecification(long branchId,
                                                                  String search,
                                                                  BookingType bookingType,
                                                                  BookingStatus bookingStatus,
                                                                  String category,
                                                                  Instant startDate,
                                                                  Instant endDate) {
        return new Specification<Pickuplog>() {
            @Override
            public Predicate toPredicate(Root<Pickuplog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Path<Booking> booking = root.get("booking");

                List<Predicate> predicates = new ArrayList<>();

                // 1. Booking Type
                if (bookingType != null) {
                    predicates.add(cb.equal(booking.get("bookingType"), bookingType));
                }

                // 2. Booking Status
                if (bookingStatus != null) {
                    predicates.add(cb.equal(booking.get("bookingStatus"), bookingStatus));
                }

                // 3. Category
                if( category != null){
                    predicates.add(cb.equal(booking.get("category").get("name"), category));
                }

                // 4. Search
                if (search != null && !search.trim().isEmpty()) {
                    String trimmedSearch = search.trim();
                    String searchPattern = "%" + trimmedSearch.toLowerCase() + "%";

                    List<Predicate> searchSubPredicates = new ArrayList<>();

                    // Add all text match queries
                    searchSubPredicates.add(cb.like(cb.lower(booking.get("customer").get("user").get("name")), searchPattern));
                    searchSubPredicates.add(cb.like(cb.lower(booking.get("vehicle").get("manufacturer")), searchPattern));
                    searchSubPredicates.add(cb.like(cb.lower(booking.get("vehicle").get("model")), searchPattern));
                    searchSubPredicates.add(cb.like(cb.lower(booking.get("returnBranch").get("name")), searchPattern));
                    searchSubPredicates.add(cb.like(cb.lower(booking.get("returnBranch").get("city")), searchPattern));
                    searchSubPredicates.add(cb.like(cb.lower(booking.get("pickupBranch").get("name")), searchPattern));
                    searchSubPredicates.add(cb.like(cb.lower(booking.get("pickupBranch").get("city")), searchPattern));
                    searchSubPredicates.add(cb.like(cb.lower(root.get("employee").get("user").get("name")), searchPattern));

                    // BookingId Search Match
                    if(trimmedSearch.matches("\\d+")) {
                        searchSubPredicates.add(cb.equal(booking.get("id"), Long.valueOf(trimmedSearch)));
                        searchSubPredicates.add(cb.equal(root.get("id"), Long.valueOf(trimmedSearch)));
                    }

                    // Combine all search fields into a single OR expression
                    Predicate searchPredicate = cb.or(searchSubPredicates.toArray(new Predicate[0]));

                    // Add the combined OR block to your main list of predicates
                    predicates.add(searchPredicate);
                }

                // 5. Date Range
                if (startDate != null && endDate != null) {
                    Predicate dateRange =  cb.or(
                            cb.between(booking.get("plannedPickup"), startDate, endDate),
                            cb.between(booking.get("plannedReturn"), startDate, endDate));

                    predicates.add(dateRange);
                }

                // 6. Branch Matching to Employee Branch
                if (branchId > 0) {
                    predicates.add(cb.equal(booking.get("pickupBranch").get("id"), branchId));
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
