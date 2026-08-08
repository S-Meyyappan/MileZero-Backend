package com.springboot.milezero.repository;

import com.springboot.milezero.enums.FeatureGroup;
import com.springboot.milezero.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FeatureRepository extends JpaRepository<Feature, Long> {

    List<Feature> findByFeatureGroup(FeatureGroup featureGroup);

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM VehicleFeature vf WHERE vf.vehicle.id = :id
        """)
    void deleteAllByVehicleId(long id);
}
