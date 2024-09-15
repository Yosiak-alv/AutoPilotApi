package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.persistence.dto.car.CarDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.car.CarListDTO;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.car.CarRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ConflictException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Car;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.CarRepository;
import com.faithjoyfundation.autopilotapi.v1.services.BranchService;
import com.faithjoyfundation.autopilotapi.v1.services.CarService;
import com.faithjoyfundation.autopilotapi.v1.services.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelService modelService;
    private final BranchService branchService;

    @Override
    public PaginatedResponse<CarListDTO> findAllBySearch(String search, Long branchId, Long brandId, Long modelId, int page, int size) {
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        return new PaginatedResponse<>(carRepository
                .findAllBySearch(search, branchId, brandId, modelId, pageable).map(CarListDTO::new));
    }

    @Override
    public CarDTO findDTOById(Long id) {
        return new CarDTO(findModelById(id));
    }

    @Override
    public Car findModelById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Car not found with id " + id));
    }

    @Override
    public CarDTO create(CarRequest request) {
        return saveOrUpdate(new Car(), request, null);
    }

    @Override
    public CarDTO update(Long id, CarRequest request) {
        return saveOrUpdate(this.findModelById(id), request, id);
    }

    @Override
    public boolean delete(Long id) {
        Car car = this.findModelById(id);
        if (car.getRepairs().isEmpty()) {
            carRepository.delete(car);
            return true;
        }
        throw new ConflictException("Car has repairs associated with it and cannot be deleted.");
    }

    private CarDTO saveOrUpdate(Car car, CarRequest request, Long existingId) {
        validateUniquePlateAndVIN(request.getPlates(), request.getVIN(), existingId);
        car.setPlates(request.getPlates());
        car.setVIN(request.getVIN());
        car.setColor(request.getColor());
        car.setYear(request.getYear());
        car.setCurrentMileage(request.getMileage());
        car.setMotorID(request.getMotorId());
        car.setModel(modelService.findModelById(request.getModelId()));
        car.setBranch(branchService.findModelById(request.getBranchId()));

        car = carRepository.save(car);
        return new CarDTO(car);
    }

    private void validateUniquePlateAndVIN(String plates, String VIN, Long existingId) throws BadRequestException {
        Optional<Car> existingCar = carRepository.findByPlates(plates);
        if (existingCar.isPresent() && !existingCar.get().getId().equals(existingId)) {
            throw new BadRequestException("Car with plates " + plates + " already exists.");
        }

        existingCar = this.carRepository.findByVIN(VIN);
        if (existingCar.isPresent() && !existingCar.get().getId().equals(existingId)) {
            throw new BadRequestException("Car with VIN " + VIN + " already exists.");
        }
    }

    private void validatePageNumberAndSize(int page, int size) throws BadRequestException {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
        if (size <= 0) {
            throw new BadRequestException("Size number cannot be less than or equal to zero.");
        }
    }
}
