package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandDTO;
import com.faithjoyfundation.autopilotapi.v1.exceptions.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.models.Brand;
import com.faithjoyfundation.autopilotapi.v1.repositories.BrandRepository;
import com.faithjoyfundation.autopilotapi.v1.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public PaginatedResponse<BrandListDTO> findAll(String search, int page, int size) {
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<BrandListDTO> brands;
        if (search == null || search.isEmpty()) {
            brands = brandRepository.findAllOrderedById(pageable).map(BrandListDTO::new);
        } else {
            brands = brandRepository.findByNameContainingOrderById(search, pageable).map(BrandListDTO::new);
        }
        return new PaginatedResponse<>(brands);
    }

    @Override
    public BrandDTO findById(Long id) {
        Brand brand = findModelById(id);
        return new BrandDTO(brand);
    }

    @Override
    public BrandDTO create(BrandRequest brandRequest) {
        validateUniqueField(brandRequest.getName(), null);
        Brand brand = new Brand(null, brandRequest.getName(),null);
        return new BrandDTO(brandRepository.save(brand));
    }

    @Override
    public BrandDTO update(Long id, BrandRequest brandRequest) {
        Brand brand = findModelById(id);
        validateUniqueField(brandRequest.getName(), id);
        brand.setName(brandRequest.getName());
        return new BrandDTO(brandRepository.save(brand));
    }

    @Override
    public BrandDTO deleteById(Long id) {
        return null;
    }

    @Override
    public Brand findModelById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada con id: " + id));
    }

    private void validateUniqueField(String name, Long existingId) throws BadRequestException {
        Optional<Brand> brand = brandRepository.findByName(name);

        if(brand.isPresent() && !brand.get().getId().equals(existingId)) {
            throw new BadRequestException("Ya existe una marca con ese nombre");
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
