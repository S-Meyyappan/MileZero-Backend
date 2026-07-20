package com.springboot.milezero.repository;

import com.springboot.milezero.model.Returnlog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReturnlogRepository extends JpaRepository<Returnlog, Long> {

    Optional<Returnlog> findByBookingId(long id);
    //Derived Method
}
