package com.faithjoyfundation.autopilotapi.v1.dto.cars;

import com.faithjoyfundation.autopilotapi.v1.models.Car;
import lombok.Data;

@Data
public class CarListDTO {
    private Long id;

    private String plates;

    private String VIN;

    private Integer year;

    private String model;

    private String branch;

    public CarListDTO(Car car){
        this.id = car.getId();
        this.plates = car.getPlates();
        this.VIN = car.getVIN();
        this.year = car.getYear();
        this.model = car.getModel().getName();
        this.branch = car.getBranch().getName();
    }
}
