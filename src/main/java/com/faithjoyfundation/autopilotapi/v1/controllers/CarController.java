package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarRepairRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.repairs.RepairRequest;
import com.faithjoyfundation.autopilotapi.v1.services.CarService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Car", description = "Car API")
@RequestMapping("/api/v1/cars")
@RestController
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<?> index(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        PaginatedResponse<CarDTO> cars = carService.findAll(search, page, size);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.ok(carService.findDTOById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CarRequest carRequest) {
        CarDTO carDTO = carService.create(carRequest);
        return (carDTO != null) ? ResponseEntity.status(201).body(carDTO) : ResponseEntity.internalServerError().build();
    }

    @PostMapping("/{id}/repair")
    public ResponseEntity<?> createRepair(@PathVariable Long id, @Valid @RequestBody CarRepairRequest carRepairRequestRequest) {
        CarDTO carDTO = carService.createRepair(id, carRepairRequestRequest);
        return (carDTO != null) ? ResponseEntity.status(201).body(carDTO) : ResponseEntity.internalServerError().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CarRequest carRequest) {
        CarDTO carDTO = carService.update(id,  carRequest);
        return (carDTO != null) ? ResponseEntity.status(200).body(carDTO) : ResponseEntity.internalServerError().build();
    }
}
