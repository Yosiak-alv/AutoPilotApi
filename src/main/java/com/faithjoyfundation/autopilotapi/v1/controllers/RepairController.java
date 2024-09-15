package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair.RepairRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair.UpdateRepairStatusRequest;
import com.faithjoyfundation.autopilotapi.v1.services.RepairService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Repair", description = "API endpoints for repairs, authentication required")
@RestController
@RequestMapping("/api/v1/repairs")
public class RepairController {
    @Autowired
    private RepairService repairService;

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @Operation(summary = "Get all repairs of a car", description = "Get all repairs with pagination, filters and search, all users can access this endpoint")
    @GetMapping("/car/{carId}")
    public ResponseEntity<?> index(
            @Parameter(description = "Car ID to filter repairs by car")
            @PathVariable(value = "carId") Long carId,

            @Parameter(description = "Page number for pagination, default 0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of records per page default 10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Filter by Workshop ID")
            @RequestParam(required = false) Long workshopId,

            @Parameter(description = "Filter by Repair Status ID")
            @RequestParam(required = false) Long repairStatusId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(repairService.findAllBySearch(carId, workshopId, repairStatusId, page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @Operation(summary = "Get a repair by ID", description = "Get a repair by ID, all users can access this endpoint")
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), repairService.findDTOById(id)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create a new repair", description = "Create a new repair, only admins and managers can access this endpoint")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RepairRequest repairRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Repair Successfully Created", repairService.create(repairRequest)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Update a repair", description = "Update a repair by ID, only admins and managers can access this endpoint, cannot update a repair that is already completed or canceled")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody RepairRequest repairRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(),"Repair Successfully Updated", repairService.update(id, repairRequest)));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Update a repair status", description = "Update a repair status by ID, only admins can access this endpoint")
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateRepairStatus(@PathVariable Long id,
                                                                     @Valid @RequestBody  UpdateRepairStatusRequest updateRepairStatusRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(),"Repair Status Successfully Updated", repairService.updateRepairStatus(id, updateRepairStatusRequest)));
    }

    //@PreAuthorize("hasAnyRole('ADMIN')") is already set on security configuration
    @Operation(summary = "Delete a repair", description = "Delete a repair by ID, only admins can access this endpoint")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = repairService.delete(id);
        return deleted ? ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Repair Deleted Successfully")) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete repair"));
    }
}
