package com.faithjoyfundation.autopilotapi.v1.services;


import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.*;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Repair;

public interface RepairService {

    PaginatedResponse<RepairListDTO> findAllBySearch(Long carId, String search, int page, int size);

    RepairDTO findDTOById(Long id);

    Repair findModelById(Long id);

    RepairDTO create(RepairRequest repairRequest);

    RepairDTO update(Long id, RepairRequest repairRequest);

    RepairDTO updateRepairStatus(Long id, UpdateRepairStatusRequest request);

    boolean delete(Long id);
}
