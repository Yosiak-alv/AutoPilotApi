package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.car.CarRequest;
import com.faithjoyfundation.autopilotapi.v1.services.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cars", description = "API endpoints for Cars, authentication required")
@RestController
@RequestMapping("/api/v1/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @Operation(summary = "Get all cars", description = "Get all cars with pagination, filters and search, Admins, Managers and Employees")
    @GetMapping
    public  ResponseEntity<?> index(
            @Parameter(description = "Search term to filter by plates, VIN, or year, Example: 2021")
            @RequestParam(required = false, defaultValue = "") String search,

            @Parameter(description = "Page number for pagination, default 0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of records per page default 10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Filter by Branch ID")
            @RequestParam(required = false) Long branchId,

            @Parameter(description = "Filter by Brand ID")
            @RequestParam(required = false) Long brandId,

            @Parameter(description = "Filter by Model ID")
            @RequestParam(required = false) Long modelId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(carService.findAllBySearch(search, branchId, brandId, modelId, page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @Operation(summary = "Get a car by ID", description = "Get a car by ID, Admins, Managers and Employees")
    @GetMapping("/{id}")
    public  ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), carService.findDTOById(id)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create a new car", description = "Create a new car, Admins and Managers only")
    @PostMapping
    public  ResponseEntity<?> store(@Valid @RequestBody CarRequest carRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Car Created Successfully", carService.create(carRequest)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Update a car", description = "Update a car by ID, Admins and Managers only")
    @PutMapping("/{id}")
    public  ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CarRequest carRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Car Updated Successfully", carService.update(id, carRequest)));
    }

    //@PreAuthorize("hasRole('ADMIN')") is already set on security configuration
    @Operation(summary = "Delete a car", description = "Delete a car by ID, Admins only")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = carService.delete(id);
        return deleted ? ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Car deleted successfully with id " + id)) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occurred while deleting car"));
    }
}
