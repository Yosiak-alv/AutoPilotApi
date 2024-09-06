package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarRepairRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.relationships.CarRepairDTO;
import com.faithjoyfundation.autopilotapi.v1.models.Car;

import java.util.List;

public interface CarService {
    PaginatedResponse<CarListDTO> findAll(String search, int page, int size);
    CarDTO findDTOById(Long id);
    List<CarRepairDTO> findRepairsDTOById(Long id);
    Car findModelById(Long id);
    CarDTO create(CarRequest carRequest);
    //CarDTO createRepair(Long id, CarRepairRequest carRepairRequest);
    CarDTO update(Long id, CarRequest carRequest);
    CarDTO deleteById(Long id);
}
