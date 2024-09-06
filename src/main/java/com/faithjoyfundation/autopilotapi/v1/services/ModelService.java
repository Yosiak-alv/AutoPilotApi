package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelDTO;
import com.faithjoyfundation.autopilotapi.v1.models.Model;

public interface ModelService {
    PaginatedResponse<ModelListDTO> findAll(Long brandId,String search, int page, int size);
    ModelDTO findById(Long id);
    Model findModelById(Long id);
    ModelDTO create(ModelRequest modelRequest);
    ModelDTO update(Long id, ModelRequest modelRequest);
    ModelDTO deleteById(Long id);
}
