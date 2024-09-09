package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.ModelDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.ModelListDTO;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.ModelRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Model;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.ModelRepository;
import com.faithjoyfundation.autopilotapi.v1.services.BrandService;
import com.faithjoyfundation.autopilotapi.v1.services.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;

    private final BrandService brandService;

    @Override
    public PaginatedResponse<ModelListDTO> findAllBySearch(Long brandId,String search, int page, int size) {
        findModelById(brandId);
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return new PaginatedResponse<>(modelRepository.findAllBySearchAndBrand(brandId, search, pageable).map(ModelListDTO::new));
    }

    @Override
    public ModelDTO findDTOById(Long id) {
        return new ModelDTO(findModelById(id));
    }

    @Override
    public Model findModelById(Long id) {
        return modelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Model not found with id " + id));
    }

    @Override
    public ModelDTO create(ModelRequest modelRequest) {
        return saveOrUpdate(new Model(), modelRequest, null);
    }

    @Override
    public ModelDTO update(Long id, ModelRequest modelRequest) {
        return saveOrUpdate(this.findModelById(id), modelRequest, id);
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    private ModelDTO saveOrUpdate(Model model, ModelRequest modelRequest, Long existingId) {
        validateUniqueName(modelRequest.getName(), existingId);
        model.setName(modelRequest.getName());
        model.setBrand(brandService.findModelById(modelRequest.getBrandId()));
        model = modelRepository.save(model);
        return new ModelDTO(model);
    }


    private void validateUniqueName(String name, Long existingId) throws BadRequestException {
        Optional<Model> model = modelRepository.findByName(name);

        if(model.isPresent() && !model.get().getId().equals(existingId)) {
            throw new BadRequestException("Model with name " + name + " already exists.");
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
