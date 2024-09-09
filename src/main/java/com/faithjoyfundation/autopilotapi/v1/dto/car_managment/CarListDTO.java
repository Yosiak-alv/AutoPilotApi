package com.faithjoyfundation.autopilotapi.v1.dto.car_managment;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Car;
import lombok.Data;

@Data
public class CarListDTO {
    private Long id;

    private String plates;

    private Integer year;

    private String model;

    private String brand;

    private String branch;

    public CarListDTO(Car car){
        this.id = car.getId();
        this.plates = car.getPlates();
        this.year = car.getYear();
        this.model = car.getModel().getName();
        this.brand = car.getModel().getBrand().getName();
        this.branch = car.getBranch().getName();
    }
}
