package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelUpdateRequest;
import com.faithjoyfundation.autopilotapi.v1.models.Brand;
import com.faithjoyfundation.autopilotapi.v1.models.Model;
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

    @GetMapping
    public ResponseEntity<?> index(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return ResponseEntity.ok(modelService.findAll(search, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.ok(modelService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ModelCreateRequest modelCreateRequest) {
        ModelDTO model = modelService.create(modelCreateRequest);
        return (model != null) ? ResponseEntity.status(201).body(model) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ModelUpdateRequest modelUpdateRequest) {
        ModelDTO model = modelService.update(id, modelUpdateRequest);
        return (model != null) ? ResponseEntity.status(200).body(model) : ResponseEntity.badRequest().build();
    }
}
