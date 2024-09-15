package com.faithjoyfundation.autopilotapi.v1.persistence.dto.car;

import com.faithjoyfundation.autopilotapi.v1.persistence.dto.branch.BranchListDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.brand_model.ModelDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Car;
import lombok.Data;

@Data
public class CarListDTO {
    private Long id;

    private String plates;

    private String VIN;

    private Integer year;

    private ModelDTO model;

    private BranchListDTO branch;

    public CarListDTO(Car car){
        this.id = car.getId();
        this.plates = car.getPlates();
        this.VIN = car.getVIN();
        this.year = car.getYear();
        this.model = new ModelDTO(car.getModel());
        this.branch = new BranchListDTO(car.getBranch());
    }
}
