package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.car_managment.CarDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.car_managment.CarListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.car_managment.CarRequest;
import com.faithjoyfundation.autopilotapi.v1.services.CarService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cars", description = "API endpoints for Cars, authentication required")
@RestController
@RequestMapping("/api/v1/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<CarListDTO>> index(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findAllBySearch(search, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CarDTO>> show(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), carService.findDTOById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CarDTO>> store(@Valid @RequestBody CarRequest carRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Car Created Successfully", carService.create(carRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CarDTO>> update(@PathVariable Long id, @Valid @RequestBody CarRequest carRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Car Updated Successfully", carService.update(id, carRequest)));
    }
}
