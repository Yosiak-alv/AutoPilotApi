package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchUpdateRequest;

import com.faithjoyfundation.autopilotapi.v1.models.Branch;

public interface BranchService {
    PaginatedResponse<BranchDTO> findAll(String search, int page, int size);
    BranchDTO findDTOById(Long id);
    Branch findModelById(Long id);
    BranchDTO create(BranchCreateRequest branchCreateRequest);
    BranchDTO update(Long id, BranchUpdateRequest branchUpdateRequest);
    BranchDTO deleteById(Long id);
}
