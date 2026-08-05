package com.springboot.milezero.utility;

import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.BookingType;
import com.springboot.milezero.model.Booking;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookingFilterSpecification {

    public static Specification<Booking> getFilterSpecification(String search,
                                                                BookingType bookingType,
                                                                BookingStatus bookingStatus,
                                                                String category
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
                    String searchPattern = "%" + search.toLowerCase() + "%";

                    // Combine all search fields into a single OR expression
                    Predicate searchPredicate = cb.or(
                            cb.like(cb.lower(bookingRoot.get("customer").get("user").get("name")), searchPattern),
                            cb.like(cb.lower(bookingRoot.get("vehicle").get("manufacturer")), searchPattern),
                            cb.like(cb.lower(bookingRoot.get("vehicle").get("model")), searchPattern),
                            cb.like(cb.lower(bookingRoot.get("returnBranch").get("name")), searchPattern),
                            cb.like(cb.lower(bookingRoot.get("returnBranch").get("city")), searchPattern),
                            cb.like(cb.lower(bookingRoot.get("pickupBranch").get("name")), searchPattern),
                            cb.like(cb.lower(bookingRoot.get("pickupBranch").get("city")), searchPattern)
                    );

                    // Add the combined OR block to your main list of predicates
                    predicates.add(searchPredicate);
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
