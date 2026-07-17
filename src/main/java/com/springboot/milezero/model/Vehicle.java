package com.springboot.milezero.model;

import com.springboot.milezero.enums.CurrentStatus;
import com.springboot.milezero.enums.DriveType;
import com.springboot.milezero.enums.FuelType;
import com.springboot.milezero.enums.Transmission;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "vehicle")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String registrationNo;

    @Column(nullable = false)
    private String chassisNo;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int manufacturingYear;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @Enumerated(EnumType.STRING)
    private DriveType driveType;

    @Enumerated(EnumType.STRING)
    private CurrentStatus currentStatus;

    @Column(nullable = false)
    private long currentOdometer;

    @Column(nullable = false)
    private LocalDate purchaseDate;

    @Column(nullable = false)
    private Boolean withAc;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "current_branch_id", nullable = false)
    private Branch branch;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}
