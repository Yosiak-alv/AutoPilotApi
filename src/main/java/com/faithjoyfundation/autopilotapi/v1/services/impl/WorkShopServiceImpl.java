package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopUpdateRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.FieldUniqueException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.models.Municipality;
import com.faithjoyfundation.autopilotapi.v1.models.WorkShop;
import com.faithjoyfundation.autopilotapi.v1.repositories.WorkShopRepository;
import com.faithjoyfundation.autopilotapi.v1.services.MunicipalityService;
import com.faithjoyfundation.autopilotapi.v1.services.WorkShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkShopServiceImpl implements WorkShopService {

    @Autowired
    private WorkShopRepository workshopRepository;

    @Autowired
    private MunicipalityService municipalityService;

    @Override
    public PaginatedResponse<WorkShopDTO> findAll(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WorkShopDTO> workShopDTOS;
        if (search == null || search.isEmpty()) {
            workShopDTOS = workshopRepository.findAllOrderedById(pageable).map(
                    workShop -> new WorkShopDTO(workShop, true)
            );
        } else {
            workShopDTOS = workshopRepository.findAllBySearch(search, pageable).map(
                    workShop -> new WorkShopDTO(workShop, true)
            );
        }
        return new PaginatedResponse<>(workShopDTOS);
    }

    @Override
    public WorkShopDTO findDTOById(Long id) {
        WorkShop workShop = this.findModelById(id);
        return new WorkShopDTO(workShop, true);
    }

    @Override
    public WorkShop findModelById(Long id) {
        return this.workshopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workshop not found with id: " + id));
    }

    @Override
    public WorkShopDTO create(WorkShopCreateRequest workShopCreateRequest) {
        Municipality municipality = this.municipalityService.findById(workShopCreateRequest.getMunicipalityId());
        validateUniqueFields(workShopCreateRequest.getEmail(), workShopCreateRequest.getPhone(), null);

        WorkShop workShop = new WorkShop();
        workShop.setName(workShopCreateRequest.getName());
        workShop.setEmail(workShopCreateRequest.getEmail());
        workShop.setPhone(workShopCreateRequest.getPhone());
        workShop.setAddress(workShopCreateRequest.getAddress());
        workShop.setMunicipality(municipality);
        return new WorkShopDTO(this.workshopRepository.save(workShop), true);
    }

    @Override
    public WorkShopDTO update(Long id, WorkShopUpdateRequest workShopUpdateRequest) {
        WorkShop workShop = this.findModelById(id);
        Municipality municipality = this.municipalityService.findById(workShopUpdateRequest.getMunicipalityId());

        validateUniqueFields(workShopUpdateRequest.getEmail(), workShopUpdateRequest.getPhone(), id);

        workShop.setName(workShopUpdateRequest.getName());
        workShop.setEmail(workShopUpdateRequest.getEmail());
        workShop.setPhone(workShopUpdateRequest.getPhone());
        workShop.setAddress(workShopUpdateRequest.getAddress());
        workShop.setMunicipality(municipality);
        return new WorkShopDTO(this.workshopRepository.save(workShop), true);
    }

    @Override
    public WorkShopDTO deleteById(Long id) {
        return null;
    }

    private void validateUniqueFields(String email, String phone, Long existingId) throws FieldUniqueException {

        Optional<WorkShop> existingWorkShop = this.workshopRepository.findByEmail(email);
        if (existingWorkShop.isPresent() && !existingWorkShop.get().getId().equals(existingId)) {
            throw new FieldUniqueException("email already exists on another workshop");
        }

        existingWorkShop = this.workshopRepository.findByPhone(phone);
        if (existingWorkShop.isPresent() && !existingWorkShop.get().getId().equals(existingId)) {
            throw new FieldUniqueException("phone already exists on another workshop");
        }
    }
}
