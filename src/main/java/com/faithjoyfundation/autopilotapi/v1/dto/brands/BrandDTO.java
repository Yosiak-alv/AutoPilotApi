package com.faithjoyfundation.autopilotapi.v1.dto.brands;

import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelDTO;
import com.faithjoyfundation.autopilotapi.v1.models.Brand;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BrandDTO {
    private Long id;

    private String name;

    @JsonIgnoreProperties({"brand"})
    private Set<ModelDTO> models;

    public BrandDTO(Brand brand, boolean includeModels) {
        this.id = brand.getId();
        this.name = brand.getName();
        if (includeModels) {
            this.models = brand.getModels().stream()
                    .map(model -> new ModelDTO(model, false))
                    .collect(Collectors.toSet());
        }
    }

    public Brand toModel() {
        Brand brand = new Brand();
        brand.setId(this.id);
        brand.setName(this.name);
        brand.setModels(this.models.stream().map(ModelDTO::toModel).collect(Collectors.toSet()));
        return brand;
    }
}
