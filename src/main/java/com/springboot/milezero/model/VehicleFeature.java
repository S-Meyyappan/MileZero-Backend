package com.springboot.milezero.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicle_feature")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Feature feature;
}
