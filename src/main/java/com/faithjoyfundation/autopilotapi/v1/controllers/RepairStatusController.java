package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.RepairStatus;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.RepairStatusRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Repair Status", description = "Only GET method is allowed, for populating dropdowns, no authentication required")
@RestController
@RequestMapping("/api/v1/repair-statuses")
public class RepairStatusController {
    @Autowired
    private RepairStatusRepository repairStatusRepository;

    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), repairStatusRepository.findAll()));
    }
}
