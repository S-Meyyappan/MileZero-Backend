package com.springboot.milezero.repository;

import com.springboot.milezero.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
