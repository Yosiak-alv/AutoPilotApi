package com.faithjoyfundation.autopilotapi.v1.dto.brands;

import com.faithjoyfundation.autopilotapi.v1.dto.brands.relationships.ModelDTO;
import com.faithjoyfundation.autopilotapi.v1.models.Brand;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class BrandDTO {
    private Long id;

    private String name;

    private Set<ModelDTO> models = new HashSet<>();

    public BrandDTO(Brand brand) {
        BeanUtils.copyProperties(brand, this);
        this.models = brand.getModels().stream().map(ModelDTO::new).collect(Collectors.toSet());
    }
}
