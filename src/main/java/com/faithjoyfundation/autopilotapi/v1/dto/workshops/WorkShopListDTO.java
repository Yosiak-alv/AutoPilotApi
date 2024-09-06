package com.faithjoyfundation.autopilotapi.v1.dto.workshops;

import com.faithjoyfundation.autopilotapi.v1.models.WorkShop;
import lombok.Data;

@Data
public class WorkShopListDTO {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String municipality;

    private String department;

    public WorkShopListDTO(WorkShop workShop) {
        this.id = workShop.getId();
        this.name = workShop.getName();
        this.email = workShop.getEmail();
        this.phone = workShop.getPhone();
        this.municipality = workShop.getMunicipality().getName();
        this.department = workShop.getMunicipality().getDepartment().getName();
    }
}
