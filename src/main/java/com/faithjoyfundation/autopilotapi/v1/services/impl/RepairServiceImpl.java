package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.RepairDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.RepairListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.RepairDetailRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.RepairRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.*;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.RepairDetailRepository;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.RepairRepository;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.RepairStatusRepository;
import com.faithjoyfundation.autopilotapi.v1.services.CarService;
import com.faithjoyfundation.autopilotapi.v1.services.RepairService;
import com.faithjoyfundation.autopilotapi.v1.services.WorkShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepairServiceImpl implements RepairService {
    private final RepairRepository repairRepository;
    private final RepairDetailRepository repairDetailRepository;
    private final RepairStatusRepository repairStatusRepository;

    private final CarService carService;
    private final WorkShopService workShopService;

    @Override
    public PaginatedResponse<RepairListDTO> findAllBySearch(Long carId,String search, int page, int size) {
        validatePageNumberAndSize(page, size);
        Car car = carService.findModelById(carId);
        Pageable pageable = PageRequest.of(page, size);
        return new PaginatedResponse<>(repairRepository.findAllBySearch(car.getId(), search, pageable).map(RepairListDTO::new));
    }

    @Override
    public RepairDTO findDTOById(Long id) {
        return new RepairDTO(findModelById(id));
    }

    @Override
    public Repair findModelById(Long id) {
        return repairRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Repair not found with id " + id));
    }

    @Override
    public RepairDTO create(RepairRequest repairRequest) {
        Car car = this.carService.findModelById(repairRequest.getCarId());
        WorkShop workShop = this.workShopService.findModelById(repairRequest.getWorkshopId());
        RepairStatus repairStatus = repairStatusRepository
                .findById(repairRequest.getRepairStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("RepairStatus not found with id " + repairRequest.getRepairStatusId()));

        Repair repair = new Repair();
        repair.setCar(car);
        repair.setWorkshop(workShop);
        repair.setRepairStatus(repairStatus);
        repair.setRepairDetails(repairRequest.toRepairDetails(repair));
        repair.setTotal(repair.calculateTotal());
        repair = repairRepository.save(repair);
        return new RepairDTO(repair);
    }

    @Override
    public RepairDTO update(Long id, RepairRequest repairRequest) {
        Repair repair = this.findModelById(id);
        Car car = this.carService.findModelById(repairRequest.getCarId());
        WorkShop workShop = this.workShopService.findModelById(repairRequest.getWorkshopId());
        RepairStatus repairStatus = repairStatusRepository
                .findById(repairRequest.getRepairStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("RepairStatus not found with id " + repairRequest.getRepairStatusId()));

        if (!repair.getRepairDetails().isEmpty()) {
            repairDetailRepository.deleteByRepairId(repair.getId());
        }

        repair.setCar(car);
        repair.setWorkshop(workShop);
        repair.setRepairStatus(repairStatus);
        repair.setRepairDetails(repairRequest.toRepairDetails(repair));
        repair.setTotal(repair.calculateTotal());
        repair = repairRepository.save(repair);
        return new RepairDTO(repair);
    }

    @Override
    public RepairDTO addRepairDetail(Long id, RepairDetailRequest repairDetailRequest) {
        return null;
    }

    @Override
    public RepairDTO updateRepairDetail(Long id, Long repairDetailId, RepairDetailRequest repairDetailRequest) {
        return null;
    }

    @Override
    public RepairDTO deleteRepairDetail(Long id, Long repairDetailId) {
        return null;
    }

    @Override
    public RepairDTO updateRepairStatus(Long id, Long repairStatusId) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        Repair repair = this.findModelById(id);
        if (repair != null) {
            repairDetailRepository.deleteAll(repair.getRepairDetails());
            repairRepository.delete(repair);
            return true;
        }
        return false;
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
