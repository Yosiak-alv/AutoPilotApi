package com.faithjoyfundation.autopilotapi.v1.dto.models.relationships;

import com.faithjoyfundation.autopilotapi.v1.models.Brand;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class BrandDTO {
    private Long id;

    private String name;

    public BrandDTO(Brand brand) {
        BeanUtils.copyProperties(brand, this);
    }
}
