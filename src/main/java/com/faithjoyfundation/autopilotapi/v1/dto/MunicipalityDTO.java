package com.faithjoyfundation.autopilotapi.v1.dto;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Municipality;
import lombok.Data;

@Data
public class MunicipalityDTO {
    private Long id;

    private String name;

    private DepartmentDTO department;

    public MunicipalityDTO(Municipality municipality) {
        this.id = municipality.getId();
        this.name = municipality.getName();
        this.department = new DepartmentDTO(municipality.getDepartment());
    }
}
