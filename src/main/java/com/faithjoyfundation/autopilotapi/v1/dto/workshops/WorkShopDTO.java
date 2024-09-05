package com.faithjoyfundation.autopilotapi.v1.dto.workshops;

import com.faithjoyfundation.autopilotapi.v1.dto.MunicipalityDTO;
import com.faithjoyfundation.autopilotapi.v1.models.WorkShop;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkShopDTO {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    private MunicipalityDTO municipality;

    public WorkShopDTO(WorkShop workShop, boolean includeMunicipality) {
        this.id = workShop.getId();
        this.name = workShop.getName();
        this.email = workShop.getEmail();
        this.phone = workShop.getPhone();
        this.address = workShop.getAddress();
        this.created = workShop.getCreatedAt();
        this.updated = workShop.getUpdatedAt();
        if (includeMunicipality) {
            this.municipality = new MunicipalityDTO(workShop.getMunicipality());
        }
    }
}
