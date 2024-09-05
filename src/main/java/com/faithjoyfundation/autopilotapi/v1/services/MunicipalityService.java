package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.models.Department;
import com.faithjoyfundation.autopilotapi.v1.models.Municipality;

import java.util.List;

public interface MunicipalityService {
    Municipality findById(Long id);
}
