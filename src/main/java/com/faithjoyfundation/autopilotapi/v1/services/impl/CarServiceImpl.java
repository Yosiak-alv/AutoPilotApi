package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarRepairRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.FieldUniqueException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.models.*;
import com.faithjoyfundation.autopilotapi.v1.repositories.CarRepository;
import com.faithjoyfundation.autopilotapi.v1.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    @Autowired
    private WorkShopService workShopService;

    @Autowired
    private RepairStatusService repairStatusService;

    @Autowired
    private RepairService repairService;

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
    public CarDTO createRepair(Long id, CarRepairRequest carRepairRequest) {
        Car car = this.findModelById(id);
        WorkShop workShop = workShopService.findModelById(carRepairRequest.getWorkshopId());
        RepairStatus repairStatus = repairStatusService.findById(carRepairRequest.getRepairStatusId());

        Repair repair = new Repair();
        repair.setCar(car);
        repair.setWorkshop(workShop);
        repair.setRepairStatus(repairStatus);
        repair.setTotal(carRepairRequest.calculateTotal());
        repair.setRepairDetails(carRepairRequest.getDetails().stream().map(
                repairDetailRequest -> {
                    RepairDetail repairDetail = new RepairDetail();
                    repairDetail.setRepair(repair);
                    repairDetail.setName(repairDetailRequest.getName());
                    repairDetail.setDescription(repairDetailRequest.getDescription());
                    repairDetail.setPrice(repairDetailRequest.getPrice());
                    return repairDetail;
                }
        ).collect(Collectors.toSet()));

        Repair savedRepair = repairService.create(repair);
        car = this.findModelById(id);

        return new CarDTO(car, true, true, true);
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
