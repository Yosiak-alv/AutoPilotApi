package com.faithjoyfundation.autopilotapi.v1.persistence.dto.workshop;


import com.faithjoyfundation.autopilotapi.v1.persistence.dto.MunicipalityDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.WorkShop;
import lombok.Data;

@Data
public class WorkShopListDTO {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private MunicipalityDTO municipality;

    public WorkShopListDTO(WorkShop workShop) {
        this.id = workShop.getId();
        this.name = workShop.getName();
        this.email = workShop.getEmail();
        this.phone = workShop.getPhone();
        this.municipality = new MunicipalityDTO(workShop.getMunicipality());
    }
}
