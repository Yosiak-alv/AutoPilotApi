package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.exceptions.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.models.RepairStatus;
import com.faithjoyfundation.autopilotapi.v1.repositories.RepairStatusRepository;
import com.faithjoyfundation.autopilotapi.v1.services.RepairStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairStatusServiceImpl implements RepairStatusService {

    @Autowired
    private RepairStatusRepository repairStatusRepository;

    @Override
    public List<RepairStatus> findAll() {
        return this.repairStatusRepository.findAllOrderedById();
    }

    @Override
    public RepairStatus findById(Long id) {
        return this.repairStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RepairStatus not found with id: " + id));
    }
}
