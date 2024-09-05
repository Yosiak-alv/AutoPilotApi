package com.faithjoyfundation.autopilotapi.v1.controllers;

import com.faithjoyfundation.autopilotapi.v1.services.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Departments", description = "Departments API only get all department with municipalities for select boxes")
@RequestMapping("/api/v1/departments")
@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.ok(departmentService.findAll());
    }
}
