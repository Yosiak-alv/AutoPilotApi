package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.models.Repair;
import com.faithjoyfundation.autopilotapi.v1.repositories.RepairRepository;
import com.faithjoyfundation.autopilotapi.v1.services.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairServiceImpl implements RepairService {
    @Autowired
    private RepairRepository repairRepository;

    @Override
    public Repair create(Repair repair) {
        return this.repairRepository.save(repair);
    }
}
