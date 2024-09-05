package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchListDTO;
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
    public PaginatedResponse<BranchListDTO> findAll(String search, int page, int size) {
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<BranchListDTO> branches;
        if (search == null || search.isEmpty()) {
            branches = branchRepository.findAllOrderedById(pageable).map(BranchListDTO::new);
        } else {
            branches = branchRepository.findAllBySearch(search, pageable).map(BranchListDTO::new);
        }
        return new PaginatedResponse<>(branches);
    }

    @Override
    public BranchDTO findDTOById(Long id) {
        Branch branch = this.findModelById(id);
        return new BranchDTO(branch);
    }

    @Override
    public Branch findModelById(Long id) {
        return this.branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con id: " + id));
    }

    @Override
    public BranchDTO create(BranchRequest branchRequest) {
        return this.saveOrUpdateBranch(new Branch(), branchRequest, null);
    }

    @Override
    public BranchDTO update(Long id, BranchRequest branchRequest) {
        Branch existingBranch = this.findModelById(id);
        return this.saveOrUpdateBranch(existingBranch, branchRequest, id);
    }

    @Override
    public BranchDTO deleteById(Long id) {
        return null;
    }

    private BranchDTO saveOrUpdateBranch(Branch branch, BranchRequest branchRequest, Long id) {
        Municipality municipality = this.municipalityService.findById(branchRequest.getMunicipalityId());
        validateUniqueFields(branchRequest.getEmail(), branchRequest.getPhone(), id);

        branch.setName(branchRequest.getName());
        branch.setEmail(branchRequest.getEmail());
        branch.setPhone(branchRequest.getPhone());
        branch.setAddress(branchRequest.getAddress());
        branch.setMain(branchRequest.isMain());
        branch.setMunicipality(municipality);
        branch = this.branchRepository.save(branch);
        return new BranchDTO(branch);
    }

    private void validateUniqueFields(String email, String phone, Long existingId) throws FieldUniqueException {

        Optional<Branch> existingBranch = this.branchRepository.findByEmail(email);
        if (existingBranch.isPresent() && !existingBranch.get().getId().equals(existingId)) {
            throw new BadRequestException("correo electrónico ya existe en otra sucursal");
        }

        existingBranch = this.branchRepository.findByPhone(phone);
        if (existingBranch.isPresent() && !existingBranch.get().getId().equals(existingId)) {
            throw new BadRequestException("teléfono ya existe en otra sucursal");
        }
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("el número de página no puede ser menor que cero.");
        }

        if (size <= 0) {
            throw new BadRequestException("el tamaño de la página no puede ser menor o igual a cero.");
        }
    }
}
