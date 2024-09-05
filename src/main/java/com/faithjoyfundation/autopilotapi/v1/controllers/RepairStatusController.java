package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.repositories.RepairStatusRepository;
import com.faithjoyfundation.autopilotapi.v1.services.RepairStatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Repair Statuses", description = "Repair Statuses API only get all repair statuses for select box")
@RequestMapping("/api/v1/repair-statuses")
@RestController
public class RepairStatusController {

    @Autowired
    private RepairStatusService repairStatusService;

    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.ok(this.repairStatusService.findAll());
    }
}
