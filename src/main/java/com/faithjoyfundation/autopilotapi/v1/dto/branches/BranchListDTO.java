package com.faithjoyfundation.autopilotapi.v1.dto.branches;

import com.faithjoyfundation.autopilotapi.v1.models.Branch;
import lombok.Data;

@Data
public class BranchListDTO {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String municipality;

    private String department;

    public BranchListDTO(Branch branch) {
        this.id = branch.getId();
        this.name = branch.getName();
        this.email = branch.getEmail();
        this.phone = branch.getPhone();
        this.municipality = branch.getMunicipality().getName();
        this.department = branch.getMunicipality().getDepartment().getName();
    }
}
