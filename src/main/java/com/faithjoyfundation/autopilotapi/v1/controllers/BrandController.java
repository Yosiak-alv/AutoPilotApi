package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandUpdateRequest;
import com.faithjoyfundation.autopilotapi.v1.models.Brand;
import com.faithjoyfundation.autopilotapi.v1.services.BrandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Brands", description = "Brands API")
@RequestMapping("/api/v1/brands")
@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<?> index(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        PaginatedResponse<BrandDTO> brands = brandService.findAll(search, page, size);
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BrandCreateRequest brandCreateRequest) {
        BrandDTO brand = brandService.create(brandCreateRequest);
        return (brand != null) ? ResponseEntity.status(201).body(brand) : ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody BrandUpdateRequest brandRequest) {
        BrandDTO brand = brandService.update(id, brandRequest);
        return (brand != null) ? ResponseEntity.status(200).body(brand) : ResponseEntity.internalServerError().build();
    }
}
