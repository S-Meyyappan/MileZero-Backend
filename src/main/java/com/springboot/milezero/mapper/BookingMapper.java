package com.springboot.milezero.mapper;

import com.springboot.milezero.dto.request.BookingReqDto;
import com.springboot.milezero.dto.response.*;
import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.model.Booking;
import com.springboot.milezero.model.BookingAddon;

import java.util.List;

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

    public static BookingRespDto convertEntityToDto(Booking booking, List<BookingAddon> bookingAddons){
        return new BookingRespDto(
                booking.getId(),
                booking.getBookedRate(),
                booking.getBookingType(),
                booking.getBookingStatus(),
                booking.getEstimatedCost(),
                booking.getFinalCost(),
                booking.getDuration(),
                booking.getIncludedKm(),
                booking.getPlannedPickup(),
                booking.getPlannedReturn(),
                new CustomerRespDto(
                        booking.getCustomer().getId(),
                        booking.getCustomer().getUser().getName(),
                        booking.getCustomer().getLicenseNo(),
                        booking.getCustomer().getLicenseExpiryDate(),
                        booking.getCustomer().getPhone(),
                        booking.getCustomer().getInsurancePolicyNo(),
                        booking.getCustomer().getLicensePhoto()),
                new BranchRespDto(
                        booking.getPickupBranch().getId(),
                        booking.getPickupBranch().getName(),
                        booking.getPickupBranch().getCity(),
                        booking.getPickupBranch().getAddress(),
                        booking.getPickupBranch().getPhone()),
                new BranchRespDto(
                        booking.getReturnBranch().getId(),
                        booking.getReturnBranch().getName(),
                        booking.getReturnBranch().getCity(),
                        booking.getReturnBranch().getAddress(),
                        booking.getReturnBranch().getPhone()),
                new VehicleDto(
                        booking.getVehicle().getId(),
                        booking.getVehicle().getManufacturer(),
                        booking.getVehicle().getModel(),
                        booking.getVehicle().getRegistrationNo(),
                        booking.getVehicle().getCurrentOdometer(),
                        booking.getVehicle().getManufacturingYear(),
                        booking.getVehicle().getFuelType(),
                        booking.getVehicle().getTransmission(),
                        booking.getVehicle().getDriveType(),
                        booking.getVehicle().getWithAc(),
                        booking.getVehicle().getSeatCount(),
                        booking.getVehicle().getImage(),
                        booking.getVehicle().getLuggageCapacity(),
                        new CategoryRespDto(
                                booking.getCategory().getId(),
                                booking.getCategory().getName(),
                                booking.getCategory().getBasePricePerDay(),
                                booking.getCategory().getBasePricePerHour(),
                                booking.getCategory().getBasePricePerKm(),
                                booking.getCategory().getIncludedKmPerDay()
                        )),
                bookingAddons.stream()
                        .map(BookingAddonMapper::convertEntityToDto)
                        .toList()
        );
    }
}
