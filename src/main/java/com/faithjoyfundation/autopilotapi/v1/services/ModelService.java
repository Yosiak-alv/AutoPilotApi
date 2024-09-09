package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.ModelDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.ModelListDTO;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.ModelRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Model;

public interface ModelService {

        PaginatedResponse<ModelListDTO> findAllBySearch(Long brandId, String search, int page, int size);

        ModelDTO findDTOById(Long id);

        Model findModelById(Long id);

        ModelDTO create(ModelRequest modelRequest);

        ModelDTO update(Long id, ModelRequest modelRequest);

        boolean delete(Long id);
}
