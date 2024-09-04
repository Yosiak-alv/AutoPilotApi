package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelUpdateRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.FieldUniqueException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.models.Brand;
import com.faithjoyfundation.autopilotapi.v1.models.Model;
import com.faithjoyfundation.autopilotapi.v1.repositories.ModelRepository;
import com.faithjoyfundation.autopilotapi.v1.services.BrandService;
import com.faithjoyfundation.autopilotapi.v1.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModelServiceImpl implements ModelService {
    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private BrandService brandService;

    @Override
    public PaginatedResponse<ModelDTO> findAll(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ModelDTO> models;
        if (search == null || search.isEmpty()) {
           models = modelRepository.findAllOrderedById(pageable).map(
                    model -> new ModelDTO(model, true)
           );
        } else {
            models = modelRepository.findByNameContainingOrderById(search, pageable).map(
                    model -> new ModelDTO(model, true)
            );
        }
        return new PaginatedResponse<>(models);
    }

    @Override
    public ModelDTO findById(Long id) {
        Model model = findModelById(id);
        return new ModelDTO(model, true);
    }

    @Override
    public ModelDTO create(ModelCreateRequest modelCreateRequest) {
        Brand brand = brandService.findModelById(modelCreateRequest.getBrandId());
        validateUniqueField(modelCreateRequest.getName(), null);

        Model model = new Model();
        model.setName(modelCreateRequest.getName());
        model.setBrand(brand);
        return new ModelDTO(modelRepository.save(model), true);
    }

    @Override
    public ModelDTO update(Long id, ModelUpdateRequest modelUpdateRequest) {
        //validations
        Model model = findModelById(id);
        Brand brand = brandService.findModelById(modelUpdateRequest.getBrandId());
        validateUniqueField(modelUpdateRequest.getName(), id);

        model.setName(modelUpdateRequest.getName());
        model.setBrand(brand);
        return new ModelDTO(modelRepository.save(model), true);
    }

    @Override
    public ModelDTO deleteById(Long id) {
        return null;
    }

    @Override
    public Model findModelById(Long id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Model not found with id " + id));
    }

    private void validateUniqueField(String name, Long existingId) throws FieldUniqueException {
        Optional<Model> model = modelRepository.findByName(name);

        if(model.isPresent() && !model.get().getId().equals(existingId)) {
            throw new FieldUniqueException("Model with that name already exists");
        }
    }
}
