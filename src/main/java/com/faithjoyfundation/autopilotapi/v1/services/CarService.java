package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarRequest;
import com.faithjoyfundation.autopilotapi.v1.models.Car;

public interface CarService {
    PaginatedResponse<CarDTO> findAll(String search, int page, int size);
    CarDTO findDTOById(Long id);
    Car findModelById(Long id);
    CarDTO create(CarRequest carRequest);
    CarDTO update(Long id, CarRequest carRequest);
    CarDTO deleteById(Long id);
}
