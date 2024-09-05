package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandUpdateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopUpdateRequest;
import com.faithjoyfundation.autopilotapi.v1.models.WorkShop;

public interface WorkShopService {
    PaginatedResponse<WorkShopDTO> findAll(String search, int page, int size);
    WorkShopDTO findDTOById(Long id);
    WorkShop findModelById(Long id);
    WorkShopDTO create(WorkShopCreateRequest workShopCreateRequest);
    WorkShopDTO update(Long id, WorkShopUpdateRequest workShopUpdateRequest);
    WorkShopDTO deleteById(Long id);
}
