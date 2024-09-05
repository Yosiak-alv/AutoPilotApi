package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.dto.repairs.RepairDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.repairs.RepairDetailRequest;
import com.faithjoyfundation.autopilotapi.v1.models.Repair;

import java.util.List;

public interface RepairService {
    Repair create(Repair repair);
}
