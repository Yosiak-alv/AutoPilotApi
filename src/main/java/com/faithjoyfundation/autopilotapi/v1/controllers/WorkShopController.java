package com.faithjoyfundation.autopilotapi.v1.controllers;


import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopUpdateRequest;
import com.faithjoyfundation.autopilotapi.v1.services.WorkShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Workshops", description = "Workshops API")
@RequestMapping("/api/v1/workshops")
@RestController
public class WorkShopController {

    @Autowired
    private WorkShopService workShopService;

    @GetMapping
    public ResponseEntity<?> index(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        PaginatedResponse<WorkShopDTO> workShops = workShopService.findAll(search, page, size);
        return ResponseEntity.ok(workShops);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.ok(workShopService.findDTOById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody WorkShopCreateRequest workShopCreateRequest) {
        WorkShopDTO workShopDTO = workShopService.create(workShopCreateRequest);
        return (workShopDTO != null) ? ResponseEntity.status(201).body(workShopDTO) : ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody WorkShopUpdateRequest workShopUpdateRequest) {
        WorkShopDTO workShopDTO = workShopService.update(id, workShopUpdateRequest);
        return (workShopDTO != null) ? ResponseEntity.status(200).body(workShopDTO) : ResponseEntity.internalServerError().build();
    }
}
