package com.faithjoyfundation.autopilotapi.v1.dto.models;

import com.faithjoyfundation.autopilotapi.v1.models.Model;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ModelListDTO {
    private Long id;

    private String name;

    public ModelListDTO(Model model) {
        BeanUtils.copyProperties(model, this);
    }
}
