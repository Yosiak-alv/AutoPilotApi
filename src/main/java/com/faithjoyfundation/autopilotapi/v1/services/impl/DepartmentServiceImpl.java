package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.exceptions.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.models.Department;
import com.faithjoyfundation.autopilotapi.v1.repositories.DepartmentRepository;
import com.faithjoyfundation.autopilotapi.v1.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> findAll() {
        return this.departmentRepository.findAllOrderedById();
    }

    @Override
    public Department findById(Long id) {
        return this.departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con id: " + id));
    }
}
