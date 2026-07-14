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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal basePricePerDay;

    @Column(nullable = false)
    private BigDecimal lateFeePerHour;

    @Column(nullable = false)
    private BigDecimal extraFeePerKm;
}
