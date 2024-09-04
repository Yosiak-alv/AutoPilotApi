package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelUpdateRequest;
import com.faithjoyfundation.autopilotapi.v1.models.Model;

import java.util.List;

public interface ModelService {
    PaginatedResponse<ModelDTO> findAll(String search, int page, int size);
    ModelDTO findById(Long id);
    Model findModelById(Long id);
    ModelDTO create(ModelCreateRequest modelCreateRequest);
    ModelDTO update(Long id, ModelUpdateRequest modelUpdateRequest);
    ModelDTO deleteById(Long id);
}
