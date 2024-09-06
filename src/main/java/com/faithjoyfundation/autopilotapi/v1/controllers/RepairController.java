package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.dto.repairs.RepairRequest;
import com.faithjoyfundation.autopilotapi.v1.services.RepairService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Repairs", description = "Repairs API")
@RestController
@RequestMapping("/api/v1/repairs")
public class RepairController {

    @Autowired
    private RepairService repairService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RepairRequest repairRequest) {
        return ResponseEntity.status(201).body(repairService.create(repairRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody RepairRequest repairRequest) {
        return ResponseEntity.ok(repairService.update(id, repairRequest));
    }
}
