package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandDTO;
import com.faithjoyfundation.autopilotapi.v1.services.BrandService;
import com.faithjoyfundation.autopilotapi.v1.services.ModelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Brands", description = "Brands API")
@RequestMapping("/api/v1/brands")
@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelService modelService;

    @GetMapping
    public ResponseEntity<?> index(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        PaginatedResponse<BrandListDTO> brands = brandService.findAll(search, page, size);
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BrandRequest brandRequest) {
        BrandDTO brand = brandService.create(brandRequest);
        return (brand != null) ? ResponseEntity.status(201).body(brand) : ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody BrandRequest brandRequest) {
        BrandDTO brand = brandService.update(id, brandRequest);
        return (brand != null) ? ResponseEntity.status(200).body(brand) : ResponseEntity.internalServerError().build();
    }
}
