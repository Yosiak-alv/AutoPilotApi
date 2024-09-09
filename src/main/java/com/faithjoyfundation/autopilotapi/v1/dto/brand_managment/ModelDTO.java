package com.faithjoyfundation.autopilotapi.v1.dto.brand_managment;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Model;
import lombok.Data;

@Data
public class ModelDTO {
    private Long id;

    private String name;

    private BrandListDTO brand;

    public ModelDTO(Model model) {
        this.id = model.getId();
        this.name = model.getName();
        this.brand = new BrandListDTO(model.getBrand());
    }
}
