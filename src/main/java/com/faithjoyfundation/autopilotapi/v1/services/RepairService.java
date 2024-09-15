package com.faithjoyfundation.autopilotapi.v1.services;


import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair.RepairDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair.RepairListDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair.RepairRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair.UpdateRepairStatusRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Repair;

public interface RepairService {

    PaginatedResponse<RepairListDTO> findAllBySearch(Long carId, Long workshopId, Long repairStatusId, int page, int size);

    RepairDTO findDTOById(Long id);

    Repair findModelById(Long id);

    RepairDTO create(RepairRequest repairRequest);

    RepairDTO update(Long id, RepairRequest repairRequest);

    RepairDTO updateRepairStatus(Long id, UpdateRepairStatusRequest request);

    boolean delete(Long id);
}
