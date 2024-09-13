package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.branch_managment.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.branch_managment.BranchListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.branch_managment.BranchRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Branch;
import com.faithjoyfundation.autopilotapi.v1.services.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Branches", description = "API endpoints for Branches, authentication required")
@RestController
@RequestMapping("/api/v1/branches")
public class BranchController {
    @Autowired
    private BranchService branchService;

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @Operation(summary = "Get all branches", description = "Get all branches with pagination, filters and search, all users can access this endpoint")
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
        return ResponseEntity.status(HttpStatus.OK).body(branchService.findAllBySearch(search, municipalityId, departmentId, page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @Operation(summary = "Get a branch by ID", description = "Get a branch by ID, all users can access this endpoint")
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.value(), branchService.findDTOById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new branch", description = "Create a new branch, only admins can access this endpoint")
    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody BranchRequest branchRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Branch Created Successfully", branchService.create(branchRequest)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a branch", description = "Update a branch by ID, only admins can access this endpoint")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody BranchRequest branchRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Branch Updated Successfully", branchService.update(id, branchRequest)));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a branch", description = "Delete a branch by ID, only admins can access this endpoint")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = branchService.delete(id);
        return deleted ? ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Branch deleted successfully")) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Branch could not be deleted"));
    }
}
