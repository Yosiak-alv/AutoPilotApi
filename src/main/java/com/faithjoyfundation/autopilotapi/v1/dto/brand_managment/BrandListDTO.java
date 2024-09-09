package com.faithjoyfundation.autopilotapi.v1.dto.brand_managment;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Brand;
import lombok.Data;

@Data
public class BrandListDTO {
    private Long id;

    private String name;

    public BrandListDTO(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }
}
