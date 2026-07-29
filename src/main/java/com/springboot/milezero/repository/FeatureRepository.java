package com.springboot.milezero.repository;

import com.springboot.milezero.enums.FeatureGroup;
import com.springboot.milezero.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeatureRepository extends JpaRepository<Feature, Long> {

    List<Feature> findByFeatureGroup(FeatureGroup featureGroup);
}
