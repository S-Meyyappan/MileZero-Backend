package com.springboot.milezero.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "return_image")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReturnImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    private Booking booking;

    @ManyToOne
    private Returnlog returnlog;
}
