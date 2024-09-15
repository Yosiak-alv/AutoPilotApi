package com.faithjoyfundation.autopilotapi.v1.persistence.dto;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Department;
import lombok.Data;

@Data
public class DepartmentDTO {
    private Long id;
    private String name;

    public DepartmentDTO(Department department) {
        this.id = department.getId();
        this.name = department.getName();
    }
}
