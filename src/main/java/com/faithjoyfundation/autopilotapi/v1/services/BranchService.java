package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.dto.branch_managment.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.branch_managment.BranchListDTO;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.branch_managment.BranchRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Branch;

public interface BranchService {

    PaginatedResponse<BranchListDTO> findAllBySearch(String search, Long municipalityId, Long departmentId, int page, int size);

    BranchDTO findDTOById(Long id);

    Branch findModelById(Long id);

    BranchDTO create(BranchRequest branchRequest);

    BranchDTO update(Long id, BranchRequest branchRequest);

    boolean delete(Long id);
}
