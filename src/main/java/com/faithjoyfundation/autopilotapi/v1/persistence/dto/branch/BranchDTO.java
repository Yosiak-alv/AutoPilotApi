package com.faithjoyfundation.autopilotapi.v1.persistence.dto.branch;

import com.faithjoyfundation.autopilotapi.v1.persistence.dto.MunicipalityDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Branch;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BranchDTO {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String address;

    private boolean main;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    private MunicipalityDTO municipality;

    public BranchDTO(Branch branch) {
        this.id = branch.getId();
        this.name = branch.getName();
        this.email = branch.getEmail();
        this.phone = branch.getPhone();
        this.address = branch.getAddress();
        this.main = branch.isMain();
        this.created = branch.getCreatedAt();
        this.updated = branch.getUpdatedAt();
        this.municipality = new MunicipalityDTO(branch.getMunicipality());
    }
}
