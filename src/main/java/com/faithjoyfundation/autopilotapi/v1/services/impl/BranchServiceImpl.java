package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.persistence.dto.branch.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.branch.BranchListDTO;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.branch.BranchRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ConflictException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Branch;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Municipality;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.BranchRepository;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.MunicipalityRepository;
import com.faithjoyfundation.autopilotapi.v1.services.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    private final MunicipalityRepository municipalityRepository;

    @Override
    public PaginatedResponse<BranchListDTO> findAllBySearch(String search, Long municipalityId, Long departmentId, int page, int size) {
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        return new PaginatedResponse<>(branchRepository.findAllBySearch(search,municipalityId, departmentId, pageable).map(BranchListDTO::new));
    }

    @Override
    public BranchDTO findDTOById(Long id) {
        return new BranchDTO(findModelById(id));
    }

    @Override
    public Branch findModelById(Long id) {
        return branchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found with id " + id));
    }

    @Override
    public BranchDTO create(BranchRequest branchRequest) {
        return saveOrUpdate(new Branch(), branchRequest, null);
    }

    @Override
    public BranchDTO update(Long id, BranchRequest branchRequest) {
        return saveOrUpdate(this.findModelById(id), branchRequest, id);
    }

    @Override
    public boolean delete(Long id) {
        Branch branch = this.findModelById(id);
        if (branch.getCars().isEmpty()){
            branchRepository.delete(branch);
            return true;
        }
        throw new ConflictException("Branch cannot be deleted because it has cars associated with it.");
    }

    private BranchDTO saveOrUpdate(Branch branch, BranchRequest branchRequest, Long existingId) {
        Municipality municipality = municipalityRepository
                .findById(branchRequest.getMunicipalityId())
                .orElseThrow(() -> new ResourceNotFoundException("Municipality not found with id " + branchRequest.getMunicipalityId()));
        validateUniqueEmailAndPhone(branchRequest.getEmail(), branchRequest.getPhone(), existingId);
        branch.setName(branchRequest.getName());
        branch.setEmail(branchRequest.getEmail());
        branch.setPhone(branchRequest.getPhone());
        branch.setAddress(branchRequest.getAddress());
        branch.setMain(branchRequest.isMain());
        branch.setMunicipality(municipality);
        branch = branchRepository.save(branch);
        return new BranchDTO(branch);
    }

    private void validateUniqueEmailAndPhone(String email, String phone, Long existingId) {
        Optional<Branch> existingBranch = this.branchRepository.findByEmail(email);
        if (existingBranch.isPresent() && !existingBranch.get().getId().equals(existingId)) {
            throw new BadRequestException("Branch with email " + email + " already exists.");
        }

        existingBranch = this.branchRepository.findByPhone(phone);
        if (existingBranch.isPresent() && !existingBranch.get().getId().equals(existingId)) {
            throw new BadRequestException("Branch with phone " + phone + " already exists.");
        }
    }

    private void validatePageNumberAndSize(int page, int size) throws BadRequestException {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
        if (size <= 0) {
            throw new BadRequestException("Size number cannot be less than or equal to zero.");
        }
    }
}
