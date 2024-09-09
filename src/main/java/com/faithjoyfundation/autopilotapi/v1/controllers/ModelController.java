package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.ModelDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.ModelListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.ModelRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.car_managment.CarListDTO;
import com.faithjoyfundation.autopilotapi.v1.services.ModelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Models", description = "API endpoints for Models, authentication required")
@RestController
@RequestMapping("/api/v1/models")
public class ModelController {
    @Autowired
    private ModelService modelService;

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<PaginatedResponse<ModelListDTO>> index(
            @PathVariable(value = "brandId") Long brandId,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(modelService.findAllBySearch(brandId, search, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ModelDTO>> show(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), modelService.findDTOById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ModelDTO>> store(@Valid @RequestBody ModelRequest modelRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Model Created Successfully", modelService.create(modelRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ModelDTO>> update(@PathVariable Long id, @Valid @RequestBody ModelRequest modelRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Model Updated Successfully", modelService.update(id, modelRequest)));
    }
}
