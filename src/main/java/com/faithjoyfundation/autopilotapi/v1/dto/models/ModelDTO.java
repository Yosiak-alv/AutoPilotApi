package com.faithjoyfundation.autopilotapi.v1.dto.models;

import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandDTO;
import com.faithjoyfundation.autopilotapi.v1.models.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class ModelDTO {
    private Long id;

    private String name;

    @JsonIgnoreProperties({"models"})
    private BrandDTO brand;

    public ModelDTO(Model model, boolean includeBrand) {
        this.id = model.getId();
        this.name = model.getName();
        if (includeBrand) {
            this.brand = new BrandDTO(model.getBrand(), false);
        }
    }

    public Model toModel() {
        Model model = new Model();
        model.setId(this.id);
        model.setName(this.name);
        model.setBrand(this.brand.toModel());
        return model;
    }
}
