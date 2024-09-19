package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.workshop.WorkShopRequest;
import com.faithjoyfundation.autopilotapi.v1.services.WorkShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Workshops", description = "API endpoints for Workshops, authentication required")
@RestController
@RequestMapping("api/v1/workshops")
public class WorkShopController {
    @Autowired
    private WorkShopService workShopService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Get all workshops", description = "Get all workshops with pagination, filters and search , Admins and Managers only")
    @GetMapping
    public ResponseEntity<?> index(
            @Parameter(description = "Search term to filter by name, email, or phone, Example: 78085392")
            @RequestParam(required = false, defaultValue = "") String search,

            @Parameter(description = "Page number for pagination, default 0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of records per page default 10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Filter by Department ID")
            @RequestParam(required = false) Long departmentId,

            @Parameter(description = "Filter by Municipality ID")
            @RequestParam(required = false) Long municipalityId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(workShopService.findAllBySearch(search, municipalityId, departmentId, page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Get a workshop by ID", description = "Get a workshop by ID, Admins and Managers only")
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), workShopService.findDTOById(id)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create a new workshop", description = "Create a new workshop, Admins and Managers only")
    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody WorkShopRequest workShopRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(),"Workshop Created Successfully", workShopService.create(workShopRequest)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Update a workshop", description = "Update a workshop by ID, Admins and Managers only")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody WorkShopRequest workShopRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Workshop Updated Successfully", workShopService.update(id, workShopRequest)));
    }

    //@PreAuthorize("hasRole('ADMIN')") is already set on security configuration
    @Operation(summary = "Delete a workshop", description = "Delete a workshop by ID, Admins only")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = workShopService.delete(id);
        return deleted ? ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Workshop deleted successfully")) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete workshop"));
    }
}
