package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopDTO;
import com.faithjoyfundation.autopilotapi.v1.models.WorkShop;

public interface WorkShopService {
    PaginatedResponse<WorkShopListDTO> findAll(String search, int page, int size);
    WorkShopDTO findDTOById(Long id);
    WorkShop findModelById(Long id);
    WorkShopDTO create(WorkShopRequest workShopRequest);
    WorkShopDTO update(Long id, WorkShopRequest workShopRequest);
    WorkShopDTO deleteById(Long id);
}
