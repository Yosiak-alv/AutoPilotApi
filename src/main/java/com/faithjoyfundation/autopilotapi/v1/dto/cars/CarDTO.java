package com.faithjoyfundation.autopilotapi.v1.dto.cars;

import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.relationships.CarRepairDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.repairs.RepairDTO;
import com.faithjoyfundation.autopilotapi.v1.models.Car;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class CarDTO {
    private Long id;

    private String plates;

    private String VIN;

    private Double mileage;

    private Integer year;

    private String color;

    private String motorId;

    private ModelDTO model;

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
