package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.exceptions.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.models.Municipality;
import com.faithjoyfundation.autopilotapi.v1.repositories.MunicipalityRepository;
import com.faithjoyfundation.autopilotapi.v1.services.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipalityServiceImpl implements MunicipalityService {

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Override
    public Municipality findById(Long id) {
        return this.municipalityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Municipio no encontrado con id: " + id));
    }

}
