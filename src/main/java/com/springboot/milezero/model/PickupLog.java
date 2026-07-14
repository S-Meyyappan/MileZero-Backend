package com.springboot.milezero.model;

import com.springboot.milezero.enums.FuelLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pickup_log")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PickupLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime actualPickup;

    @Column(nullable = false)
    private long odometer;

    @Enumerated(EnumType.STRING)
    private FuelLevel fuelLevel;

    @Column(nullable = false, length = 1200)
    private String remarks;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}
