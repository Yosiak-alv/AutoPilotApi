package com.faithjoyfundation.autopilotapi.v1.persistence.dto.brand_model;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Model;
import lombok.Data;

@Data
public class ModelListDTO {
    private Long id;

    private String name;

    public ModelListDTO(Model model) {
        this.id = model.getId();
        this.name = model.getName();
    }
}
