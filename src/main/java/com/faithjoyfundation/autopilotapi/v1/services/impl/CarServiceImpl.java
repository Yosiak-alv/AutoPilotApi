package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.FieldUniqueException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.models.Branch;
import com.faithjoyfundation.autopilotapi.v1.models.Car;
import com.faithjoyfundation.autopilotapi.v1.models.Model;
import com.faithjoyfundation.autopilotapi.v1.repositories.CarRepository;
import com.faithjoyfundation.autopilotapi.v1.services.BranchService;
import com.faithjoyfundation.autopilotapi.v1.services.CarService;
import com.faithjoyfundation.autopilotapi.v1.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BranchService branchService;

    @Autowired
    private ModelService modelService;

    @Override
    public PaginatedResponse<CarDTO> findAll(String search, int page, int size) {
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<CarDTO> carDTOS;
        if (search == null || search.isEmpty()) {
            carDTOS = carRepository.findAllOrderedById(pageable).map(
                    car -> new CarDTO(car, true, true, false)
            );
        } else {
            carDTOS = carRepository.findAllBySearch(search, pageable).map(
                    car -> new CarDTO(car, true, true, false)
            );
        }
        return new PaginatedResponse<>(carDTOS);
    }

    @Override
    public CarDTO findDTOById(Long id) {
        Car car = this.findModelById(id);
        return new CarDTO(car, true, true, true);
    }

    @Override
    public Car findModelById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
    }

    @Override
    public CarDTO create(CarRequest carRequest) {
        Branch branch = branchService.findModelById(1L); // TODO: remove hardcoded value
        Model model = modelService.findModelById(carRequest.getModelId());
        validateUniqueFields(carRequest.getPlates(), carRequest.getVIN(), null);

        Car car = new Car();
        car.setPlates(carRequest.getPlates());
        car.setVIN(carRequest.getVIN());
        car.setMotorID(carRequest.getMotorId());
        car.setCurrentMileage(carRequest.getMileage());
        car.setYear(carRequest.getYear());
        car.setColor(carRequest.getColor());
        car.setBranch(branch);
        car.setModel(model);
        return new CarDTO(this.carRepository.save(car), true, true, true);
    }

    @Override
    public CarDTO update(Long id, CarRequest carRequest) {
        Car car = this.findModelById(id);
        Branch branch = branchService.findModelById(1L); // TODO: remove hardcoded value
        Model model = modelService.findModelById(carRequest.getModelId());
        validateUniqueFields(carRequest.getPlates(), carRequest.getVIN(), id);

        car.setPlates(carRequest.getPlates());
        car.setVIN(carRequest.getVIN());
        car.setMotorID(carRequest.getMotorId());
        car.setCurrentMileage(carRequest.getMileage());
        car.setYear(carRequest.getYear());
        car.setColor(carRequest.getColor());
        car.setBranch(branch);
        car.setModel(model);
        return new CarDTO(this.carRepository.save(car), true, true, true);
    }

    @Override
    public CarDTO deleteById(Long id) {
        return null;
    }

    private void validateUniqueFields(String plates, String VIN, Long existingId) {
        Optional<Car> existingCar = carRepository.findByPlates(plates);
        if (existingCar.isPresent() && !existingCar.get().getId().equals(existingId)) {
            throw new FieldUniqueException("plates already exists on another car");
        }

        existingCar = this.carRepository.findByVIN(VIN);
        if (existingCar.isPresent() && !existingCar.get().getId().equals(existingId)) {
            throw new FieldUniqueException("VIN already exists on another car");
        }
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size <= 0) {
            throw new BadRequestException("Size number cannot be less than or equal to zero.");
        }
    }
}
