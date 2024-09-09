package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.BrandDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.BrandListDTO;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.BrandRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Brand;

public interface BrandService {

    PaginatedResponse<BrandListDTO> findAllBySearch(String search, int page, int size);

    BrandDTO findDTOById(Long id);

    Brand findModelById(Long id);

    BrandDTO create(BrandRequest brandRequest);

    BrandDTO update(Long id, BrandRequest brandRequest);

    boolean delete(Long id);
}
