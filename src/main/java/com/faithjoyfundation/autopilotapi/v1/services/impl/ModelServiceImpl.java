package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.models.ModelDTO;
import com.faithjoyfundation.autopilotapi.v1.exceptions.BadRequestException;
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
    public PaginatedResponse<ModelListDTO> findAll(Long brandId, String search, int page, int size) {
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<ModelListDTO> models;
        if (search == null || search.isEmpty()) {
            models = modelRepository.findAllOrderedByIdAndBrand(brandId, pageable).map(ModelListDTO::new);
        } else {
            models = modelRepository.findAllBySearchAndBrand(brandId, search, pageable).map(ModelListDTO::new);
        }
        return new PaginatedResponse<>(models);
    }

    @Override
    public ModelDTO findById(Long id) {
        Model model = findModelById(id);
        return new ModelDTO(model);
    }

    @Override
    public ModelDTO create(ModelRequest modelRequest) {
        Brand brand = brandService.findModelById(modelRequest.getBrandId());
        validateUniqueField(modelRequest.getName(), null);

        Model model = new Model();
        model.setName(modelRequest.getName());
        model.setBrand(brand);
        return new ModelDTO(modelRepository.save(model));
    }

    @Override
    public ModelDTO update(Long id, ModelRequest modelRequest) {
        //validations
        Model model = findModelById(id);
        Brand brand = brandService.findModelById(modelRequest.getBrandId());
        validateUniqueField(modelRequest.getName(), id);

        model.setName(modelRequest.getName());
        model.setBrand(brand);
        return new ModelDTO(modelRepository.save(model));
    }

    @Override
    public ModelDTO deleteById(Long id) {
        return null;
    }

    @Override
    public Model findModelById(Long id) {
        return modelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modelo no encontrado con id: " + id));
    }

    private void validateUniqueField(String name, Long existingId) throws BadRequestException {
        Optional<Model> model = modelRepository.findByName(name);

        if(model.isPresent() && !model.get().getId().equals(existingId)) {
            throw new BadRequestException("Modelo ya existe con ese nombre");
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
