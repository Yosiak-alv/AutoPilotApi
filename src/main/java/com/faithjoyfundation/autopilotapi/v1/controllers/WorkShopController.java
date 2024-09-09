package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.workshop_managment.WorkShopDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.workshop_managment.WorkShopListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.workshop_managment.WorkShopRequest;
import com.faithjoyfundation.autopilotapi.v1.services.WorkShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Workshops", description = "API endpoints for Workshops, authentication required")
@RestController
@RequestMapping("api/v1/workshops")
public class WorkShopController {
    @Autowired
    private WorkShopService workShopService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<WorkShopListDTO>> index(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(workShopService.findAllBySearch(search, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkShopDTO>> show(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), workShopService.findDTOById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WorkShopDTO>> store(@Valid @RequestBody WorkShopRequest workShopRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(),"Workshop Created Successfully", workShopService.create(workShopRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkShopDTO>> update(@PathVariable Long id, @Valid @RequestBody WorkShopRequest workShopRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Workshop Updated Successfully", workShopService.update(id, workShopRequest)));
    }
}
