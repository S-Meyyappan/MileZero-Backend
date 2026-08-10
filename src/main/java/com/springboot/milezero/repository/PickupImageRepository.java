package com.springboot.milezero.repository;

import com.springboot.milezero.model.PickupImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PickupImageRepository extends JpaRepository<PickupImage, Long> {

    @Query("""
            SELECT p
            FROM PickupImage p
            WHERE p.pickuplog.id IN :pickupIds
            """)
    List<PickupImage> findByPickuplogIn(List<Long> pickupIds);
}
