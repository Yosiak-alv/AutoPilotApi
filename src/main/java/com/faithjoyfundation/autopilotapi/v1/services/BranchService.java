package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.persistence.dto.branch.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.branch.BranchListDTO;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.branch.BranchRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Branch;

public interface BranchService {

    PaginatedResponse<BranchListDTO> findAllBySearch(String search, Long municipalityId, Long departmentId, int page, int size);

    BranchDTO findDTOById(Long id);

    Branch findModelById(Long id);

    BranchDTO create(BranchRequest branchRequest);

    BranchDTO update(Long id, BranchRequest branchRequest);

    boolean delete(Long id);
}
