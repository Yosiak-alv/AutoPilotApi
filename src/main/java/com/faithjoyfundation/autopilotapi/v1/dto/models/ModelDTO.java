package com.faithjoyfundation.autopilotapi.v1.dto.models;

import com.faithjoyfundation.autopilotapi.v1.dto.models.relationships.BrandDTO;
import com.faithjoyfundation.autopilotapi.v1.models.Model;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class  ModelDTO {
    private Long id;

    private String name;

    private BrandDTO brand;

    public ModelDTO(Model model) {
        BeanUtils.copyProperties(model, this);
        this.brand = new BrandDTO(model.getBrand());
    }
}
