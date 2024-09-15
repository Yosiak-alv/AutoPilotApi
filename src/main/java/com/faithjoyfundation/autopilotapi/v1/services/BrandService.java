package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.persistence.dto.brand_model.BrandDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.brand_model.BrandListDTO;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.brand_model.BrandRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Brand;

public interface BrandService {

    PaginatedResponse<BrandListDTO> findAllBySearch(String search, int page, int size);

    BrandDTO findDTOById(Long id);

    Brand findModelById(Long id);

    BrandDTO create(BrandRequest brandRequest);

    BrandDTO update(Long id, BrandRequest brandRequest);

    boolean delete(Long id);
}
