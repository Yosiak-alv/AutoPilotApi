package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.services.BranchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Branches", description = "Branches API")
@RequestMapping("/api/v1/branches")
@RestController
public class BranchController {
    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<?> index(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        PaginatedResponse<BranchListDTO> branches = branchService.findAll(search, page, size);
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.findDTOById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BranchRequest branchRequest) {
        BranchDTO branchDTO = branchService.create(branchRequest);
        return (branchDTO != null) ? ResponseEntity.status(201).body(branchDTO) : ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody BranchRequest branchRequest) {
        BranchDTO branchDTO = branchService.update(id,  branchRequest);
        return (branchDTO != null) ? ResponseEntity.status(200).body(branchDTO) : ResponseEntity.internalServerError().build();
    }
}
