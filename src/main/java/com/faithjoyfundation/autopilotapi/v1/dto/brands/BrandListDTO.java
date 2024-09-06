package com.faithjoyfundation.autopilotapi.v1.dto.brands;

import com.faithjoyfundation.autopilotapi.v1.models.Brand;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class BrandListDTO {
    private Long id;

    private String name;

    public BrandListDTO(Brand brand) {
       BeanUtils.copyProperties(brand, this);
    }
}
