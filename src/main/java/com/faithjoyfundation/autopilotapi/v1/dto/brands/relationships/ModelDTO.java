package com.faithjoyfundation.autopilotapi.v1.dto.brands.relationships;

import com.faithjoyfundation.autopilotapi.v1.models.Model;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ModelDTO {
    private Long id;

    private String name;

    public ModelDTO(Model model) {
        BeanUtils.copyProperties(model, this);
    }
}
