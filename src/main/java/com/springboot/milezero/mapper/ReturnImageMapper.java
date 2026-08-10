package com.springboot.milezero.mapper;

import com.springboot.milezero.model.Booking;
import com.springboot.milezero.model.ReturnImage;
import com.springboot.milezero.model.Returnlog;

public class ReturnImageMapper {
    private ReturnImageMapper() {
        /* This utility class should not be instantiated */
    }


    public static ReturnImage createEntity(String fileName, Booking booking, Returnlog returnlog) {
        ReturnImage returnImage = new ReturnImage();
        returnImage.setUrl(fileName);
        returnImage.setBooking(booking);
        returnImage.setReturnlog(returnlog);
        return returnImage;
    }
}
