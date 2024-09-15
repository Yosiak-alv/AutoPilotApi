package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.enums.RepairStatusType;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair.RepairDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair.RepairListDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair.RepairRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair.UpdateRepairStatusRequest;
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
    public PaginatedResponse<RepairListDTO> findAllBySearch(Long carId, Long workshopId, Long repairStatusId, int page, int size) {
        validatePageNumberAndSize(page, size);
        carService.findModelById(carId);
        Pageable pageable = PageRequest.of(page, size);
        return new PaginatedResponse<>(repairRepository
                .findAllBySearch(carId, workshopId, repairStatusId, pageable).map(RepairListDTO::new));
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

        if (repair.getRepairStatus().getName().equals(RepairStatusType.COMPLETED.name()) ||
                repair.getRepairStatus().getName().equals(RepairStatusType.CANCELED.name())) {
            throw new BadRequestException("You cannot update a repair that is already completed or canceled");
        }
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
    public RepairDTO updateRepairStatus(Long id, UpdateRepairStatusRequest request) {
        Repair repair = this.findModelById(id);
        RepairStatus repairStatus = repairStatusRepository
                .findById(request.getRepairStatusId())
                .orElseThrow(() -> new BadRequestException("RepairStatus not found with id " + request.getRepairStatusId()));

        repair.setRepairStatus(repairStatus);
        repair = repairRepository.save(repair);
        return new RepairDTO(repair);
    }

    @Override
    public boolean delete(Long id) {
        Repair repair = this.findModelById(id);
        if (repair != null) {
            if(repair.getRepairStatus().getName().equals(RepairStatusType.COMPLETED.name()) ||
                    repair.getRepairStatus().getName().equals(RepairStatusType.CANCELED.name())){
                throw new BadRequestException("You cannot delete a repair that is already completed or canceled");
            }
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
