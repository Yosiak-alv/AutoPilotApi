package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.dto.repairs.RepairDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.repairs.RepairDetailRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.repairs.RepairRequest;
import com.faithjoyfundation.autopilotapi.v1.models.Repair;

import java.util.List;

public interface RepairService {

    RepairDTO findById(Long id);

    RepairDTO create(RepairRequest repairRequest);

    RepairDTO update(Long id, RepairRequest repairRequest);

    RepairDTO deleteById(Long id);
}
