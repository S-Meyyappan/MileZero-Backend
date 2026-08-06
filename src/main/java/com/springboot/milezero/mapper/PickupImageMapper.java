package com.springboot.milezero.mapper;

import com.springboot.milezero.model.Booking;
import com.springboot.milezero.model.PickupImage;
import com.springboot.milezero.model.Pickuplog;

public class PickupImageMapper {

    public static PickupImage createEntity(String fileName, Booking booking, Pickuplog pickuplog) {
        PickupImage pickupImage = new PickupImage();
        pickupImage.setUrl(fileName);
        pickupImage.setBooking(booking);
        pickupImage.setPickuplog(pickuplog);
        return pickupImage;
    }
}
