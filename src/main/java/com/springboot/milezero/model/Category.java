package com.springboot.milezero.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "category")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal basePricePerDay;

    @Column(nullable = false)
    private BigDecimal basePricePerHour;

    @Column(nullable = false)
    private BigDecimal basePricePerKm;

    @Column(nullable = false)
    private int includedKmPerDay;
}
