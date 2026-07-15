package com.springboot.milezero.model;

import com.springboot.milezero.enums.BookingStatus;
import com.springboot.milezero.enums.BookingType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
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

    @Column(nullable = true)
    private BigDecimal finalCost; // Added during return

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    private BookingType bookingType;

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
    @JoinColumn(name = "vehicle_id", nullable = true) // Added during pickup
    private Vehicle vehicle;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
