package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.dto.repairs.RepairDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.repairs.RepairRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.models.*;
import com.faithjoyfundation.autopilotapi.v1.repositories.RepairRepository;
import com.faithjoyfundation.autopilotapi.v1.services.CarService;
import com.faithjoyfundation.autopilotapi.v1.services.RepairService;
import com.faithjoyfundation.autopilotapi.v1.services.RepairStatusService;
import com.faithjoyfundation.autopilotapi.v1.services.WorkShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RepairServiceImpl implements RepairService {
    @Autowired
    private RepairRepository repairRepository;

    @Autowired
    private RepairStatusService repairStatusService;

    @Autowired
    private WorkShopService workShopService;

    @Autowired
    private CarService carService;

    @Override
    public RepairDTO findById(Long id) {
        return new RepairDTO(this.findModelById(id));
    }

    @Override
    public RepairDTO create(RepairRequest repairRequest) {
        return this.saveOrUpdateRepair(new Repair(), repairRequest);
    }

    @Override
    public RepairDTO update(Long id, RepairRequest repairRequest) {
        Repair repair = this.findModelById(id);
        return this.saveOrUpdateRepair(repair, repairRequest);
    }

    @Override
    public RepairDTO deleteById(Long id) {
        return null;
    }

    private RepairDTO saveOrUpdateRepair(Repair repair, RepairRequest repairRequest) {
        Car car = this.carService.findModelById(repairRequest.getCarId());
        WorkShop workShop = this.workShopService.findModelById(repairRequest.getWorkshopId());
        RepairStatus repairStatus = this.repairStatusService.findById(repairRequest.getRepairStatusId());

        repair.setCar(car);
        repair.setWorkshop(workShop);
        repair.setRepairStatus(repairStatus);
        repair.setTotal(repairRequest.calculateTotal());

        repair.setRepairDetails(
                repairRequest.getDetails().stream()
                        .map(repairDetailRequest -> {
                            RepairDetail repairDetail = new RepairDetail();
                            repairDetail.setRepair(repair);
                            repairDetail.setName(repairDetailRequest.getName());
                            repairDetail.setDescription(repairDetailRequest.getDescription());
                            repairDetail.setPrice(repairDetailRequest.getPrice());
                            return repairDetail;
                        })
                        .collect(Collectors.toSet())
        );
        return new RepairDTO(this.repairRepository.save(repair));
    }

    private Repair findModelById(Long id) {
        return this.repairRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reparación no encontrada con id: " + id));
    }
}
