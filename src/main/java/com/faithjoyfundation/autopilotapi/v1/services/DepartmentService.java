package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.models.Department;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();
    Department findById(Long id);
}
