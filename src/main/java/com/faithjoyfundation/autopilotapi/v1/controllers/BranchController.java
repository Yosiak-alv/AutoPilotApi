package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchUpdateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopUpdateRequest;
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
        PaginatedResponse<BranchDTO> branches = branchService.findAll(search, page, size);
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.findDTOById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BranchCreateRequest branchCreateRequest) {
        BranchDTO branchDTO = branchService.create(branchCreateRequest);
        return (branchDTO != null) ? ResponseEntity.status(201).body(branchDTO) : ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody BranchUpdateRequest branchUpdateRequest) {
        BranchDTO branchDTO = branchService.update(id,  branchUpdateRequest);
        return (branchDTO != null) ? ResponseEntity.status(200).body(branchDTO) : ResponseEntity.internalServerError().build();
    }
}
