package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelDTO;
import com.faithjoyfundation.autopilotapi.v1.services.ModelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Models", description = "Models API")
@RequestMapping("/api/v1/models")
@RestController
public class ModelController {
    @Autowired
    private ModelService modelService;

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<?> index(
            @PathVariable(value = "brandId") Long brandId,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return ResponseEntity.ok(modelService.findAll(brandId,search, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.ok(modelService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ModelRequest modelRequest) {
        ModelDTO model = modelService.create(modelRequest);
        return (model != null) ? ResponseEntity.status(201).body(model) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ModelRequest modelRequest) {
        ModelDTO model = modelService.update(id, modelRequest);
        return (model != null) ? ResponseEntity.status(200).body(model) : ResponseEntity.badRequest().build();
    }
}
