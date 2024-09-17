package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.brand_model.BrandRequest;
import com.faithjoyfundation.autopilotapi.v1.services.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Brands", description = "API endpoints for Brands, authentication required")
@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @PreAuthorize("hasAnyRole('ADMIN' , 'MANAGER')")
    @Operation(summary = "Get all brands", description = "Get all brands with pagination, filters and search, all users can access this endpoint")
    @GetMapping
    public ResponseEntity<?> index(
            @Parameter(description = "Search term to filter by name, Example: Toyota")
            @RequestParam(required = false, defaultValue = "") String search,

            @Parameter(description = "Page number for pagination, default 0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of records per page default 10")
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.findAllBySearch(search, page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Get a brand by ID", description = "Get a brand by ID, all users can access this endpoint")
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.value(), brandService.findDTOById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new brand", description = "Create a new brand, only admins can access this endpoint")
    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody BrandRequest brandRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Brand Created Successfully" ,brandService.create(brandRequest)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a brand", description = "Update a brand, only admins can access this endpoint")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody BrandRequest brandRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(),"Brand Updated Successfully", brandService.update(id, brandRequest)));
    }

    //@PreAuthorize("hasRole('ADMIN')") is already set on security configuration
    @Operation(summary = "Delete a brand", description = "Delete a brand by ID, only admins can access this endpoint")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = brandService.delete(id);
        return deleted ? ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Brand deleted successfully with id " + id)) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occurred while deleting brand"));
    }
}
