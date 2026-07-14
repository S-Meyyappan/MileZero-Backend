package com.springboot.milezero.model;

import com.springboot.milezero.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime plannedPickup;

    @Column(nullable = false)
    private LocalDateTime plannedReturn;

    @Column(nullable = false)
    private int includedKm;

    @Column(nullable = false)
    private BigDecimal bookedRate;

    @Column(nullable = false)
    private BigDecimal estimatedCost;

    @Column(nullable = false)
    private BigDecimal finalCost;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "pickup_branch_id", nullable = false)
    private Branch pickupBranch;

    @ManyToOne
    @JoinColumn(name = "return_branch_id", nullable = false)
    private Branch returnBranch;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = true)
    private Vehicle vehicle;

}
