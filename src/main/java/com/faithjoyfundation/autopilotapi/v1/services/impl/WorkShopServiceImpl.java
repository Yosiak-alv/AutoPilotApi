package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.workshop.WorkShopDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.workshop.WorkShopListDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.workshop.WorkShopRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ConflictException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Municipality;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.WorkShop;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.MunicipalityRepository;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.WorkShopRepository;
import com.faithjoyfundation.autopilotapi.v1.services.WorkShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkShopServiceImpl implements WorkShopService {
    private final WorkShopRepository workShopRepository;
    private final MunicipalityRepository municipalityRepository;

    @Override
    public PaginatedResponse<WorkShopListDTO> findAllBySearch(String search, Long municipalityId, Long departmentId, int page, int size) {
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        return new PaginatedResponse<>(workShopRepository.findAllBySearch(search, municipalityId, departmentId, pageable).map(WorkShopListDTO::new));
    }

    @Override
    public WorkShopDTO findDTOById(Long id) {
        return new WorkShopDTO(findModelById(id));
    }

    @Override
    public WorkShop findModelById(Long id) {
        return workShopRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("WorkShop not found with id " + id));
    }

    @Override
    public WorkShopDTO create(WorkShopRequest workShopRequest) {
        return saveOrUpdate(new WorkShop(), workShopRequest, null);
    }

    @Override
    public WorkShopDTO update(Long id, WorkShopRequest workShopRequest) {
        return saveOrUpdate(this.findModelById(id), workShopRequest, id);
    }

    @Override
    public boolean delete(Long id) {
        WorkShop workShop = this.findModelById(id);
        if (workShop.getRepairs().isEmpty()) {
            workShopRepository.delete(workShop);
            return true;
        }
        throw new ConflictException("WorkShop has repairs associated with it and cannot be deleted. ");
    }

    private WorkShopDTO saveOrUpdate(WorkShop workShop, WorkShopRequest workShopRequest, Long id) {
        Municipality municipality = municipalityRepository
                .findById(workShopRequest.getMunicipalityId())
                .orElseThrow(() -> new ResourceNotFoundException("Municipality not found with id " + workShopRequest.getMunicipalityId()));
        validateUniqueEmailAndPhone(workShopRequest.getEmail(), workShopRequest.getPhone(), id);
        workShop.setName(workShopRequest.getName());
        workShop.setEmail(workShopRequest.getEmail());
        workShop.setPhone(workShopRequest.getPhone());
        workShop.setAddress(workShopRequest.getAddress());
        workShop.setMunicipality(municipality);
        workShop = workShopRepository.save(workShop);
        return new WorkShopDTO(workShop);
    }

    private void validateUniqueEmailAndPhone(String email, String phone, Long existingId) {
        Optional<WorkShop> existingWorkShop = this.workShopRepository.findByEmail(email);
        if (existingWorkShop.isPresent() && !existingWorkShop.get().getId().equals(existingId)) {
            throw new BadRequestException("WorkShop with email " + email + " already exists.");
        }

        existingWorkShop = this.workShopRepository.findByPhone(phone);
        if (existingWorkShop.isPresent() && !existingWorkShop.get().getId().equals(existingId)) {
            throw new BadRequestException("WorkShop with phone " + phone + " already exists.");
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
