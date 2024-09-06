package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandDTO;
import com.faithjoyfundation.autopilotapi.v1.models.Brand;

public interface BrandService {

    PaginatedResponse<BrandListDTO> findAll(String search, int page, int size);
    BrandDTO findById(Long id);
    Brand findModelById(Long id);
    BrandDTO create(BrandRequest brandRequest);
    BrandDTO update(Long id, BrandRequest brandRequest);
    BrandDTO deleteById(Long id);
}
