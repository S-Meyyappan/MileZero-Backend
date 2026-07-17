package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.BookingReqDto;
import com.springboot.milezero.dto.response.*;
import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.model.Booking;

public class BookingMapper {

    private BookingMapper() {
        /* This utility class should not be instantiated */
    }

    public static Booking convertDtoToEntity(BookingReqDto dto) {
        Booking booking = new Booking();
        booking.setPlannedPickup(dto.plannedPickup());
        booking.setPlannedReturn(dto.plannedReturn());
        booking.setIncludedKm(dto.requestedKm());
        booking.setBookingType(dto.bookingType());
        booking.setBookingStatus(BookingStatus.BOOKED);
        return booking;
    }

    public static BookingRespDto convertEntityToDto(Booking booking){
        return new BookingRespDto(
                booking.getId(),
                booking.getBookedRate(),
                booking.getBookingType(),
                booking.getBookingStatus(),
                booking.getEstimatedCost(),
                booking.getFinalCost(),
                booking.getIncludedKm(),
                booking.getPlannedPickup(),
                booking.getPlannedReturn(),
                new CategoryRespDto(
                        booking.getCategory().getName(),
                        booking.getCategory().getBasePricePerDay(),
                        booking.getCategory().getBasePricePerHour(),
                        booking.getCategory().getBasePricePerKm(),
                        booking.getCategory().getIncludedKmPerDay()),
                new CustomerRespDto(
                        booking.getCustomer().getName(),
                        booking.getCustomer().getLicenseNo(),
                        booking.getCustomer().getLicenseExpiryDate(),
                        booking.getCustomer().getPhone(),
                        booking.getCustomer().getInsurancePolicyNo()),
                new VehicleRespDto(
                        booking.getVehicle().getManufacturer(),
                        booking.getVehicle().getModel(),
                        booking.getVehicle().getManufacturingYear(),
                        booking.getVehicle().getFuelType(),
                        booking.getVehicle().getTransmission(),
                        booking.getVehicle().getDriveType(),
                        booking.getVehicle().getWithAc()),
                new BranchRespDto(
                        booking.getPickupBranch().getName(),
                        booking.getPickupBranch().getCity(),
                        booking.getPickupBranch().getAddress(),
                        booking.getPickupBranch().getPhone()),
                new BranchRespDto(
                        booking.getReturnBranch().getName(),
                        booking.getReturnBranch().getCity(),
                        booking.getReturnBranch().getAddress(),
                        booking.getReturnBranch().getPhone()),
                booking.getBookingAddons()
                        .stream()
                        .map(BookingAddonMapper::convertEntityToDto)
                        .toList()
        );
    }
}
