package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.RepairDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.RepairListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.RepairRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.UpdateRepairStatusRequest;
import com.faithjoyfundation.autopilotapi.v1.services.RepairService;
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
    @GetMapping("/{carId}/car")
    public ResponseEntity<?> index(
            @PathVariable Long carId,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(repairService.findAllBySearch(carId, search, page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), repairService.findDTOById(id)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RepairRequest repairRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Repair Successfully Created", repairService.create(repairRequest)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody RepairRequest repairRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(),"Repair Successfully Updated", repairService.update(id, repairRequest)));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateRepairStatus(@PathVariable Long id,
                                                                     @Valid @RequestBody  UpdateRepairStatusRequest updateRepairStatusRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(),"Repair Status Successfully Updated", repairService.updateRepairStatus(id, updateRepairStatusRequest)));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = repairService.delete(id);
        return deleted ? ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Repair Deleted Successfully")) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete repair"));
    }
}
