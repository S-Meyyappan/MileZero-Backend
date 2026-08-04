package com.springboot.milezero.repository;

import com.springboot.milezero.model.BookingAddon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingAddonRepository extends JpaRepository<BookingAddon, Long> {

    @Query("""
            SELECT b
            FROM BookingAddon b
            WHERE b.booking.id = :id
            """)
    List<BookingAddon> findAllByBooking(long id);

    @Query("""
            SELECT b
            FROM BookingAddon b
            WHERE b.booking.id IN :bookingIds
            """)
    List<BookingAddon> findAllByBookingIdIn(List<Long> bookingIds);
}
