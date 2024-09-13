package com.faithjoyfundation.autopilotapi.v1.dto.branch_managment;

import com.faithjoyfundation.autopilotapi.v1.dto.MunicipalityDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Branch;
import lombok.Data;

@Data
public class BranchListDTO {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private boolean main;

    private MunicipalityDTO municipality;

    public BranchListDTO(Branch branch) {
        this.id = branch.getId();
        this.name = branch.getName();
        this.email = branch.getEmail();
        this.phone = branch.getPhone();
        this.main = branch.isMain();
        this.municipality = new MunicipalityDTO(branch.getMunicipality());
    }
}
