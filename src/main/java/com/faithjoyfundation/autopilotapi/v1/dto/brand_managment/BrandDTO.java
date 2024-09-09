package com.faithjoyfundation.autopilotapi.v1.dto.brand_managment;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Brand;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class BrandDTO {
    private Long id;

    private String name;

    private Set<ModelListDTO> models;

    public BrandDTO(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.models = brand.getModels().stream().map(ModelListDTO::new).collect(Collectors.toSet());
    }
}
