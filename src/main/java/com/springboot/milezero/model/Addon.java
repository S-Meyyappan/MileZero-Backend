package com.springboot.milezero.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "addon")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Addon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal pricePerDay;
}
