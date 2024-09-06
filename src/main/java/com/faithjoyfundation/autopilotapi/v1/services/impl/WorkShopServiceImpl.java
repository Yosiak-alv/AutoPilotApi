package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.branches.BranchRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopDTO;
import com.faithjoyfundation.autopilotapi.v1.exceptions.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.FieldUniqueException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.models.Branch;
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
    public PaginatedResponse<WorkShopListDTO> findAll(String search, int page, int size) {
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<WorkShopListDTO> workShopDTOS;
        if (search == null || search.isEmpty()) {
            workShopDTOS = workshopRepository.findAllOrderedById(pageable).map(WorkShopListDTO::new);
        } else {
            workShopDTOS = workshopRepository.findAllBySearch(search, pageable).map(WorkShopListDTO::new);
        }
        return new PaginatedResponse<>(workShopDTOS);
    }

    @Override
    public WorkShopDTO findDTOById(Long id) {
        WorkShop workShop = this.findModelById(id);
        return new WorkShopDTO(workShop);
    }

    @Override
    public WorkShop findModelById(Long id) {
        return this.workshopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Taller no encontrado con id: " + id));
    }

    @Override
    public WorkShopDTO create(WorkShopRequest workShopRequest) {
        return this.saveOrUpdateWorkShop(new WorkShop(), workShopRequest, null);
    }

    @Override
    public WorkShopDTO update(Long id, WorkShopRequest workShopRequest) {
        WorkShop workShop = this.findModelById(id);
        return this.saveOrUpdateWorkShop(workShop, workShopRequest, id);
    }

    @Override
    public WorkShopDTO deleteById(Long id) {
        return null;
    }

    private WorkShopDTO saveOrUpdateWorkShop(WorkShop workShop, WorkShopRequest workShopRequest, Long id) {
        Municipality municipality = this.municipalityService.findById(workShopRequest.getMunicipalityId());
        validateUniqueFields(workShopRequest.getEmail(), workShopRequest.getPhone(), id);

        workShop.setName(workShopRequest.getName());
        workShop.setEmail(workShopRequest.getEmail());
        workShop.setPhone(workShopRequest.getPhone());
        workShop.setAddress(workShopRequest.getAddress());
        workShop.setMunicipality(municipality);
        workShop = this.workshopRepository.save(workShop);
        return new WorkShopDTO(workShop);
    }

    private void validateUniqueFields(String email, String phone, Long existingId) throws BadRequestException {

        Optional<WorkShop> existingWorkShop = this.workshopRepository.findByEmail(email);
        if (existingWorkShop.isPresent() && !existingWorkShop.get().getId().equals(existingId)) {
            throw new BadRequestException("correo electrónico ya existe en otro taller");
        }

        existingWorkShop = this.workshopRepository.findByPhone(phone);
        if (existingWorkShop.isPresent() && !existingWorkShop.get().getId().equals(existingId)) {
            throw new BadRequestException("teléfono ya existe en otro taller");
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
