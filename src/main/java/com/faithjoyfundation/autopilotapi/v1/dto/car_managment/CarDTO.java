package com.faithjoyfundation.autopilotapi.v1.dto.car_managment;

import com.faithjoyfundation.autopilotapi.v1.dto.branch_managment.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.ModelDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Car;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CarDTO {
    private Long id;

    private String plates;

    private String VIN;

    private Integer mileage;

    private Integer year;

    private String color;

    private String motorId;

    private ModelDTO model;

    @JsonIgnoreProperties({"created", "updated"})
    private BranchDTO branch;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    public CarDTO(Car car) {
        this.id = car.getId();
        this.plates = car.getPlates();
        this.VIN = car.getVIN();
        this.mileage = car.getCurrentMileage();
        this.year = car.getYear();
        this.color = car.getColor();
        this.motorId = car.getMotorID();
        this.created = car.getCreatedAt();
        this.updated = car.getUpdatedAt();
        this.model = new ModelDTO(car.getModel());
        this.branch = new BranchDTO(car.getBranch());
    }
}
