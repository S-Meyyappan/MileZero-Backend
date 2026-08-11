package com.springboot.milezero.service;

import com.springboot.milezero.dto.response.CategoryRespDto;
import com.springboot.milezero.dto.response.VehicleRespDto;
import com.springboot.milezero.enums.*;
import com.springboot.milezero.exception.ResourceNotFoundException;
import com.springboot.milezero.mapper.VehicleMapper;
import com.springboot.milezero.model.Branch;
import com.springboot.milezero.model.Category;
import com.springboot.milezero.model.Feature;
import com.springboot.milezero.model.Vehicle;
import com.springboot.milezero.repository.VehicleFeatureRepository;
import com.springboot.milezero.repository.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleFeatureRepository vehicleFeatureRepository;

    private VehicleMapper vehicleMapper;

    private Vehicle vehicle;
    private Branch branch;
    private Category category;
    private List<Feature> featureList;

    @BeforeEach
    public void init() {
        category = new Category(1L, "Premium SUV", new BigDecimal("120.00"), new BigDecimal("15.00"), new BigDecimal("0.45"), 250);
        branch = new Branch(1L, "Chennai Main", "Chennai", "123 Anna Salai, Mount Road, 600002", "+919876543210");
        vehicle = new Vehicle(1L, "TN-07-BY-1234", "1HGCR2F8XHA0001", "Toyota", "Camry", 2024, FuelType.HYBRID, Transmission.AMT, DriveType.AWD, CurrentStatus.AVAILABLE, 15200L, LocalDate.parse("2024-05-15"), 5, 3, true, "https://example.com", category, branch, Instant.now(), Instant.now());
        featureList = List.of(new Feature(1L, "Feature 1", FeatureGroup.SAFETY, "Description", true));

    }

    @Test
    @DisplayName("Get Vehicle Test")
    void shouldGetVehicleById() {

        // Given
        CategoryRespDto categoryResp =
                new CategoryRespDto(
                        1L,
                        "Premium SUV",
                        new BigDecimal("120.00"),
                        new BigDecimal("15.00"),
                        new BigDecimal("0.45"),
                        250
                );

        List<Feature> features =
                List.of(
                        new Feature(
                                1L,
                                "Feature 1",
                                FeatureGroup.SAFETY,
                                "Description",
                                true
                        )
                );

        VehicleRespDto expected =
                new VehicleRespDto(
                        1L,
                        "Toyota",
                        "Camry",
                        2024,
                        LocalDate.parse("2024-05-15"),
                        "1HGCR2F8XHA0001",
                        "TN-07-BY-1234",
                        "https://example.com",
                        CurrentStatus.AVAILABLE,
                        FuelType.HYBRID,
                        Transmission.AMT,
                        DriveType.AWD,
                        true,
                        5,
                        3,
                        15200L,
                        categoryResp,
                        features
                );

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(vehicleFeatureRepository.getAllFeatures(1L)).thenReturn(featureList);

        // When
        VehicleRespDto result = vehicleService.get(1L);


        // Then
        Assertions.assertEquals(expected.id(),result.id());
        Assertions.assertEquals(expected.manufacturer(),result.manufacturer());
        Assertions.assertEquals(expected.model(),result.model());
        Assertions.assertEquals(expected.category(),result.category());
        Assertions.assertEquals(1,result.features().size());
        Assertions.assertEquals(1L,result.features().get(0).getId());
        Assertions.assertEquals("Feature 1",result.features().get(0).getName());
    }

    @Test
    @DisplayName("Get Vehicle Test - Not Found")
    void shouldThrowWhenVehicleNotFound() {
        when(vehicleRepository.findById(2L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,() -> vehicleService.get(2L));
    }

    @Test
    @DisplayName("Get All Vehicles Test")
    void shouldGetAllVehicles() {

        // Given
        List<Vehicle> vehicles = List.of(vehicle);

        Page<Vehicle> vehiclePage =
                new PageImpl<>(vehicles);

        when(vehicleRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(vehiclePage);

        when(vehicleFeatureRepository.getAllByVehicleId(
                List.of(1L)
        )).thenReturn(Collections.emptyList());

        // When
        Page<VehicleRespDto> result = vehicleService.getAll(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                FilterSort.DEFAULT
        );

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getContent().size());

        VehicleRespDto resultVehicle = result.getContent().get(0);

        Assertions.assertEquals(1L, resultVehicle.id());
        Assertions.assertEquals("Toyota", resultVehicle.manufacturer());
        Assertions.assertEquals("Camry", resultVehicle.model());
        Assertions.assertEquals(2024, resultVehicle.manufacturingYear());
        Assertions.assertEquals("TN-07-BY-1234", resultVehicle.registrationNo());
        Assertions.assertEquals(CurrentStatus.AVAILABLE, resultVehicle.currentStatus());
    }

}