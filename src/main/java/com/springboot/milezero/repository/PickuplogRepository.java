package com.springboot.milezero.repository;

import com.springboot.milezero.model.Pickuplog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickuplogRepository extends JpaRepository<Pickuplog, Long> {
}
