package com.springboot.milezero.repository;

import com.springboot.milezero.model.Feature;
import com.springboot.milezero.model.VehicleFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleFeatureRepository extends JpaRepository<VehicleFeature, Long> {

    @Query("""
            SELECT vf.feature FROM VehicleFeature vf WHERE vf.vehicle.id = :vehicleId
            """)
    List<Feature> getAllFeatures(Long vehicleId);

    @Query("""
            SELECT vf FROM VehicleFeature vf WHERE vf.vehicle.id IN :vehicleIds
            """)
    List<VehicleFeature> getAllByVehicleId(List<Long> vehicleIds);
}
