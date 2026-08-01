package com.springboot.milezero.model;

import com.springboot.milezero.enums.FeatureGroup;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feature")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private FeatureGroup featureGroup;

    private String description;

    @Column(nullable = false)
    private Boolean isActive = true;
}
