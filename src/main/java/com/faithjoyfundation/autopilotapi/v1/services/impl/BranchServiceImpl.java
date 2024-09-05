package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchUpdateRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.FieldUniqueException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.models.Branch;
import com.faithjoyfundation.autopilotapi.v1.models.Municipality;
import com.faithjoyfundation.autopilotapi.v1.repositories.BranchRepository;
import com.faithjoyfundation.autopilotapi.v1.services.BranchService;
import com.faithjoyfundation.autopilotapi.v1.services.MunicipalityService;
import com.faithjoyfundation.autopilotapi.v1.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BranchServiceImpl implements BranchService {
    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private MunicipalityService municipalityService;

    @Override
    public PaginatedResponse<BranchDTO> findAll(String search, int page, int size) {
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<BranchDTO> branchDTOS;
        if (search == null || search.isEmpty()) {
            branchDTOS = branchRepository.findAllOrderedById(pageable).map(
                    branch -> new BranchDTO(branch, true)
            );
        } else {
            branchDTOS = branchRepository.findAllBySearch(search, pageable).map(
                    branch -> new BranchDTO(branch, true)
            );
        }
        return new PaginatedResponse<>(branchDTOS);
    }

    @Override
    public BranchDTO findDTOById(Long id) {
        Branch branch = this.findModelById(id);
        return new BranchDTO(branch, false);
    }

    @Override
    public Branch findModelById(Long id) {
        return this.branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + id));
    }

    @Override
    public BranchDTO create(BranchCreateRequest branchCreateRequest) {
        Municipality municipality = this.municipalityService.findById(branchCreateRequest.getMunicipalityId());
        validateUniqueFields(branchCreateRequest.getEmail(), branchCreateRequest.getPhone(), null);

        Branch branch = new Branch();
        branch.setName(branchCreateRequest.getName());
        branch.setEmail(branchCreateRequest.getEmail());
        branch.setPhone(branchCreateRequest.getPhone());
        branch.setAddress(branchCreateRequest.getAddress());
        branch.setMain(branchCreateRequest.isMain());
        branch.setMunicipality(municipality);
        branch = this.branchRepository.save(branch);
        return new BranchDTO(branch, true);
    }

    @Override
    public BranchDTO update(Long id, BranchUpdateRequest branchUpdateRequest) {
        Branch existingBranch = this.findModelById(id);
        Municipality municipality = this.municipalityService.findById(branchUpdateRequest.getMunicipalityId());
        validateUniqueFields(branchUpdateRequest.getEmail(), branchUpdateRequest.getPhone(), id);

        existingBranch.setName(branchUpdateRequest.getName());
        existingBranch.setEmail(branchUpdateRequest.getEmail());
        existingBranch.setPhone(branchUpdateRequest.getPhone());
        existingBranch.setAddress(branchUpdateRequest.getAddress());
        existingBranch.setMain(branchUpdateRequest.isMain());
        existingBranch.setMunicipality(municipality);
        existingBranch = this.branchRepository.save(existingBranch);
        return new BranchDTO(existingBranch, true);
    }

    @Override
    public BranchDTO deleteById(Long id) {
        return null;
    }

    private void validateUniqueFields(String email, String phone, Long existingId) throws FieldUniqueException {

        Optional<Branch> existingBranch = this.branchRepository.findByEmail(email);
        if (existingBranch.isPresent() && !existingBranch.get().getId().equals(existingId)) {
            throw new FieldUniqueException("Email already exists on another branch");
        }

        existingBranch = this.branchRepository.findByPhone(phone);
        if (existingBranch.isPresent() && !existingBranch.get().getId().equals(existingId)) {
            throw new FieldUniqueException("phone already exists on another branch");
        }
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size <= 0) {
            throw new BadRequestException("Size number cannot be less than or equal to zero.");
        }
    }
}
