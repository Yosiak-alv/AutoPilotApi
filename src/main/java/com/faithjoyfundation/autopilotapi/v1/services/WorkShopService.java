package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.workshop_managment.WorkShopDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.workshop_managment.WorkShopListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.workshop_managment.WorkShopRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.WorkShop;

public interface WorkShopService {

    PaginatedResponse<WorkShopListDTO> findAllBySearch(String search, int page, int size);

    WorkShopDTO findDTOById(Long id);

    WorkShop findModelById(Long id);

    WorkShopDTO create(WorkShopRequest workShopRequest);

    WorkShopDTO update(Long id, WorkShopRequest workShopRequest);

    boolean delete(Long id);
}
