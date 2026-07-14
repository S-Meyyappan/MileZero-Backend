package com.springboot.milezero.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "branch")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    private String city;

    @Column(length = 1000)
    private String address;

    private String phone;
}
