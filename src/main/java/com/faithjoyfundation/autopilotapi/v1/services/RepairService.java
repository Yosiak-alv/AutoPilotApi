package com.faithjoyfundation.autopilotapi.v1.services;


import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.RepairDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.RepairListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.RepairDetailRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.repair_managment.RepairRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Repair;

public interface RepairService {

    PaginatedResponse<RepairListDTO> findAllBySearch(Long carId, String search, int page, int size);

    RepairDTO findDTOById(Long id);

    Repair findModelById(Long id);

    RepairDTO create(RepairRequest repairRequest);

    RepairDTO update(Long id, RepairRequest repairRequest);

    RepairDTO addRepairDetail(Long id, RepairDetailRequest repairDetailRequest);

    RepairDTO updateRepairDetail(Long id, Long repairDetailId, RepairDetailRequest repairDetailRequest);

    RepairDTO deleteRepairDetail(Long id, Long repairDetailId);

    RepairDTO updateRepairStatus(Long id, Long repairStatusId);

    boolean delete(Long id);
}
