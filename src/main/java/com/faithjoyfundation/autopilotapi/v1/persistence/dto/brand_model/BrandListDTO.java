package com.faithjoyfundation.autopilotapi.v1.persistence.dto.brand_model;

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
