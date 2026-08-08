package com.springboot.milezero.utility;

import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.BookingType;
import com.springboot.milezero.model.Booking;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BookingFilterSpecification {

    public static Specification<Booking> getFilterSpecification(String search,
                                                                BookingType bookingType,
                                                                BookingStatus bookingStatus,
                                                                String category,
                                                                Instant startDate,
                                                                Instant endDate
                                                                ) {
        return new Specification<Booking>() {
            @Override
            public Predicate toPredicate(Root<Booking> bookingRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                // 1. Booking Type
                if (bookingType != null) {
                    predicates.add(cb.equal(bookingRoot.get("bookingType"), bookingType));
                }

                // 2. Booking Status
                if (bookingStatus != null) {
                    predicates.add(cb.equal(bookingRoot.get("bookingStatus"), bookingStatus));
                }

                // 3. Category
                if( category != null){
                    predicates.add(cb.equal(bookingRoot.get("category").get("name"), category));
                }

                // 4. Search
                if (search != null && !search.trim().isEmpty()) {
                    String trimmedSearch = search.trim();
                    String searchPattern = "%" + trimmedSearch.toLowerCase() + "%";

                    List<Predicate> searchSubPredicates = new ArrayList<>();

                    // Add all text match queries
                    searchSubPredicates.add(cb.like(cb.lower(bookingRoot.get("customer").get("user").get("name")), searchPattern));
                    searchSubPredicates.add(cb.like(cb.lower(bookingRoot.get("vehicle").get("manufacturer")), searchPattern));
                    searchSubPredicates.add(cb.like(cb.lower(bookingRoot.get("vehicle").get("model")), searchPattern));
                    searchSubPredicates.add(cb.like(cb.lower(bookingRoot.get("returnBranch").get("name")), searchPattern));
                    searchSubPredicates.add(cb.like(cb.lower(bookingRoot.get("returnBranch").get("city")), searchPattern));
                    searchSubPredicates.add(cb.like(cb.lower(bookingRoot.get("pickupBranch").get("name")), searchPattern));
                    searchSubPredicates.add(cb.like(cb.lower(bookingRoot.get("pickupBranch").get("city")), searchPattern));

                    // BookingId Search Match
                    if(trimmedSearch.matches("\\d+")) {
                        searchSubPredicates.add(cb.equal(bookingRoot.get("id"), Long.valueOf(trimmedSearch)));
                    }

                    // Combine all search fields into a single OR expression
                    Predicate searchPredicate = cb.or(searchSubPredicates.toArray(new Predicate[0]));

                    // Add the combined OR block to your main list of predicates
                    predicates.add(searchPredicate);
                }

                // 5. Date Range
                if (startDate != null && endDate != null) {
                    Predicate dateRange =  cb.or(
                            cb.between(bookingRoot.get("plannedPickup"), startDate, endDate),
                            cb.between(bookingRoot.get("plannedReturn"), startDate, endDate));

                    predicates.add(dateRange);
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };

    }

    public static Specification<Booking> hasCustomerEmail(String email) {
        return (root, query, cb) ->
                cb.equal(root.get("customer")
                        .get("user")
                        .get("email"), email);
    }

    public static Specification<Booking> hasEmployeeBranch(Long branchId) {
        return (root, query, cb) ->
                cb.equal(root.get("pickupBranch")
                        .get("id"), branchId);
    }
}
