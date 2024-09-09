package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.dto.car_managment.CarDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.car_managment.CarListDTO;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.car_managment.CarRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Car;

public interface CarService {
    PaginatedResponse<CarListDTO> findAllBySearch(String search, int page, int size);

    CarDTO findDTOById(Long id);

    Car findModelById(Long id);

    CarDTO create(CarRequest request);

    CarDTO update(Long id, CarRequest request);

    boolean delete(Long id);
}
