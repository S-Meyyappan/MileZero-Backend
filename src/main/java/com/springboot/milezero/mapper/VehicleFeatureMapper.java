package com.springboot.milezero.mapper;

import com.springboot.milezero.model.Feature;
import com.springboot.milezero.model.Vehicle;
import com.springboot.milezero.model.VehicleFeature;

public class VehicleFeatureMapper {

    public static VehicleFeature convertEntityToEntity(Feature feature, Vehicle vehicle) {
        VehicleFeature vehicleFeature = new VehicleFeature();
        vehicleFeature.setFeature(feature);
        vehicleFeature.setVehicle(vehicle);
        return vehicleFeature;
    }
}
