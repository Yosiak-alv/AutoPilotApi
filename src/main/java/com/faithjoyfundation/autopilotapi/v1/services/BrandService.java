package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandUpdateRequest;
import com.faithjoyfundation.autopilotapi.v1.models.Brand;

public interface BrandService {

    PaginatedResponse<BrandDTO> findAll(String search, int page, int size);
    BrandDTO findById(Long id);
    Brand findModelById(Long id);
    BrandDTO create(BrandCreateRequest brandCreateRequest);
    BrandDTO update(Long id, BrandUpdateRequest brandUpdateRequest);
    BrandDTO deleteById(Long id);
}
