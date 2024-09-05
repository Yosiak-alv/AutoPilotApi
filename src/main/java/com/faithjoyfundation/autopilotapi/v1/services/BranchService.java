package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchListDTO;

import com.faithjoyfundation.autopilotapi.v1.models.Branch;

public interface BranchService {
    PaginatedResponse<BranchListDTO> findAll(String search, int page, int size);
    BranchDTO findDTOById(Long id);
    Branch findModelById(Long id);
    BranchDTO create(BranchRequest branchRequest);
    BranchDTO update(Long id, BranchRequest branchRequest);
    BranchDTO deleteById(Long id);
}
