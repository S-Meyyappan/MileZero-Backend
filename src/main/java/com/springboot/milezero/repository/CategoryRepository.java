package com.springboot.milezero.repository;

import com.springboot.milezero.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
