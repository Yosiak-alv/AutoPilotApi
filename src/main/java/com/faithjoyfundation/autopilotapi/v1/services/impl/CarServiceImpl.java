package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.relationships.CarRepairDTO;
import com.faithjoyfundation.autopilotapi.v1.exceptions.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.models.*;
import com.faithjoyfundation.autopilotapi.v1.repositories.CarRepository;
import com.faithjoyfundation.autopilotapi.v1.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BranchService branchService;

    @Autowired
    private ModelService modelService;

    //@Autowired
    //private RepairService repairService;

    @Override
    public PaginatedResponse<CarListDTO> findAll(String search, int page, int size) {
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<CarListDTO> cars;
        if (search == null || search.isEmpty()) {
            cars = carRepository.findAllOrderedById(pageable).map(CarListDTO::new);
        } else {
            cars = carRepository.findAllBySearch(search, pageable).map(CarListDTO::new);
        }
        return new PaginatedResponse<>(cars);
    }

    @Override
    public CarDTO findDTOById(Long id) {
        Car car = this.findModelById(id);
        return new CarDTO(car);
    }

    @Override
    public List<CarRepairDTO> findRepairsDTOById(Long id) {
        Car car = this.findModelById(id);
        Set<Repair> repairs = car.getRepairs();
        return repairs.stream().map(CarRepairDTO::new).distinct().collect(Collectors.toList());
    }

    @Override
    public Car findModelById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auto no encontrado con id: " + id));
    }

    @Override
    public CarDTO create(CarRequest carRequest) {
        return saveOrUpdateCar(new Car(), carRequest, null);
    }

    @Override
    public CarDTO update(Long id, CarRequest carRequest) {
        Car car = this.findModelById(id);
        return saveOrUpdateCar(car, carRequest, id);
    }

    @Override
    public CarDTO deleteById(Long id) {
        return null;
    }

    private CarDTO saveOrUpdateCar(Car car, CarRequest carRequest, Long id) {
        Branch branch = branchService.findModelById(carRequest.getBranchId());
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
        car = this.carRepository.save(car);
        return new CarDTO(car);
    }

    private void validateUniqueFields(String plates, String VIN, Long existingId) {
        Optional<Car> existingCar = carRepository.findByPlates(plates);
        if (existingCar.isPresent() && !existingCar.get().getId().equals(existingId)) {
            throw new BadRequestException("placas ya existen en otro carro");
        }

        existingCar = this.carRepository.findByVIN(VIN);
        if (existingCar.isPresent() && !existingCar.get().getId().equals(existingId)) {
            throw new BadRequestException("VIN ya existe en otro carro");
        }
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("el número de página no puede ser menor que cero.");
        }

        if (size <= 0) {
            throw new BadRequestException("el tamaño de la página no puede ser menor o igual a cero.");
        }
    }
}
