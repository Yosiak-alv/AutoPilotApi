package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.models.RepairStatus;

import java.util.List;

public interface RepairStatusService {
    List<RepairStatus> findAll();
    RepairStatus findById(Long id);
}
