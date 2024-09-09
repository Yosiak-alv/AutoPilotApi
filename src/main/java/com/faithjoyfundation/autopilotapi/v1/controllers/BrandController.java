package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.BrandDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.BrandListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.BrandRequest;
import com.faithjoyfundation.autopilotapi.v1.services.BrandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Brands", description = "API endpoints for Brands, authentication required")
@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<BrandListDTO>> index(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.findAllBySearch(search, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandDTO>> show(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.value(), brandService.findDTOById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BrandDTO>> store(@Valid @RequestBody BrandRequest brandRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Brand Created Successfully" ,brandService.create(brandRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandDTO>> update(@PathVariable Long id, @Valid @RequestBody BrandRequest brandRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(),"Brand Updated Successfully", brandService.update(id, brandRequest)));
    }
}
