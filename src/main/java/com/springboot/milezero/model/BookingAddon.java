package com.springboot.milezero.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "booking_addon")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingAddon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "addon_id", nullable = false)
    private  Addon addon;
}
