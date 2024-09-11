package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.branch_managment.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.branch_managment.BranchListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.branch_managment.BranchRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Branch;
import com.faithjoyfundation.autopilotapi.v1.services.BranchService;
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
    @GetMapping
    public ResponseEntity<?> index(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(branchService.findAllBySearch(search, page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.value(), branchService.findDTOById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody BranchRequest branchRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Branch Created Successfully", branchService.create(branchRequest)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody BranchRequest branchRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Branch Updated Successfully", branchService.update(id, branchRequest)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = branchService.delete(id);
        return deleted ? ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Branch deleted successfully")) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Branch could not be deleted"));
    }
}
