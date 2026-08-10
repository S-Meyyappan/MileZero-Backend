package com.springboot.milezero.mapper;

import com.springboot.milezero.model.Feature;
import com.springboot.milezero.model.Vehicle;
import com.springboot.milezero.model.VehicleFeature;

public class VehicleFeatureMapper {
    private VehicleFeatureMapper() {
        /* This utility class should not be instantiated */
    }


    public static VehicleFeature convertEntityToEntity(Feature feature, Vehicle vehicle) {
        VehicleFeature vehicleFeature = new VehicleFeature();
        vehicleFeature.setFeature(feature);
        vehicleFeature.setVehicle(vehicle);
        return vehicleFeature;
    }
}
