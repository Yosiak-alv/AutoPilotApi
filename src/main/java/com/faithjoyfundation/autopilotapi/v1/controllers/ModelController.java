package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.brand_model.ModelRequest;
import com.faithjoyfundation.autopilotapi.v1.services.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Models", description = "API endpoints for Models, authentication required")
@RestController
@RequestMapping("/api/v1/models")
public class ModelController {
    @Autowired
    private ModelService modelService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Get all models of a brand", description = "Get all models of a brand with pagination and search, Admins and Managers only")
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<?> index(
            @Parameter(description = "Brand ID to filter models by brand")
            @PathVariable(value = "brandId") Long brandId,

            @Parameter(description = "Search term to filter by name, Example: Corolla")
            @RequestParam(required = false, defaultValue = "") String search,

            @Parameter(description = "Page number for pagination, default 0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of records per page default 10")
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(modelService.findAllBySearch(brandId, search, page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Get a model by ID", description = "Get a model by ID, Admins and Managers only")
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), modelService.findDTOById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new model", description = "Create a new model, Admins only")
    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody ModelRequest modelRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Model Created Successfully", modelService.create(modelRequest)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a model", description = "Update a model by ID, Admins only")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ModelRequest modelRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Model Updated Successfully", modelService.update(id, modelRequest)));
    }

    //@PreAuthorize("hasRole('ADMIN')") is already set on security configuration
    @Operation(summary = "Delete a model", description = "Delete a model by ID, Admins only")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        boolean deleted = modelService.delete(id);
        return deleted ? ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Model deleted successfully with id " + id)) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error occurred while deleting model"));
    }
}
