package com.springboot.milezero.repository;

import com.springboot.milezero.model.Pickuplog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PickuplogRepository extends JpaRepository<Pickuplog, Long> , JpaSpecificationExecutor<Pickuplog> {

    Optional<Pickuplog> findByBookingId(long id);
    // derived query
}
