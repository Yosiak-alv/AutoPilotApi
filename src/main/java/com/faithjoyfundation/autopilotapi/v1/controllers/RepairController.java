package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.RepairDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.RepairListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.RepairRequest;
import com.faithjoyfundation.autopilotapi.v1.services.RepairService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Repair", description = "API endpoints for repairs, authentication required")
@RestController
@RequestMapping("/api/v1/repairs")
public class RepairController {
    @Autowired
    private RepairService repairService;

    @GetMapping("/{carId}/car")
    public ResponseEntity<PaginatedResponse<RepairListDTO>> index(
            @PathVariable Long carId,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(repairService.findAllBySearch(carId, search, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RepairDTO>> show(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), repairService.findDTOById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RepairDTO>> create(@Valid @RequestBody RepairRequest repairRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Repair Successfully Created", repairService.create(repairRequest)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<RepairDTO>> update(@PathVariable Long id, @Valid @RequestBody RepairRequest repairRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(),"Repair Successfully Updated", repairService.update(id, repairRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<RepairDTO>> delete(@PathVariable Long id) {
        boolean deleted = repairService.delete(id);
        return deleted ? ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Repair Deleted Successfully")) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete repair"));
    }
}
