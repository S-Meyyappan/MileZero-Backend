package com.springboot.milezero.repository;

import com.springboot.milezero.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT u FROM User u where u.email = :email and u.isActive = true
            """)
    Optional<User> findByEmail(String email);
}
