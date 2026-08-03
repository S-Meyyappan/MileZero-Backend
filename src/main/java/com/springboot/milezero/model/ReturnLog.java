package com.springboot.milezero.model;

import com.springboot.milezero.enums.FuelLevel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;


@Entity
@Table(name = "return_log")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Returnlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Instant actualReturn;

    @Column(nullable = false)
    private long odometer;

    @Enumerated(EnumType.STRING)
    private FuelLevel fuelLevel;

    @Column(nullable = false)
    private BigDecimal extraCharges;

    @Column(nullable = false, length = 1200)
    private String remarks;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
