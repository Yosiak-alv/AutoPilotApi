package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.pagination.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandCreateRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brands.BrandUpdateRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.FieldUniqueException;
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
    public PaginatedResponse<BrandDTO> findAll(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BrandDTO> brands;
        if (search == null || search.isEmpty()) {
            brands = brandRepository.findAllOrderedById(pageable).map(
                    brand -> new BrandDTO(brand, false)
            );
        } else {
            brands = brandRepository.findByNameContainingOrderById(search, pageable).map(
                    brand -> new BrandDTO(brand, false)
            );
        }
        return new PaginatedResponse<>(brands);
    }

    @Override
    public BrandDTO findById(Long id) {
        Brand brand = findModelById(id);
        return new BrandDTO(brand, true);
    }

    @Override
    public BrandDTO create(BrandCreateRequest brandCreateRequest) {
        validateUniqueField(brandCreateRequest.getName(), null);
        Brand brand = new Brand(null, brandCreateRequest.getName(),null);
        return new BrandDTO(brandRepository.save(brand),false);
    }

    @Override
    public BrandDTO update(Long id, BrandUpdateRequest brandUpdateRequest) {
        Brand brand = findModelById(id);
        validateUniqueField(brandUpdateRequest.getName(), id);

        brand.setName(brandUpdateRequest.getName());
        return new BrandDTO(brandRepository.save(brand), true);
    }

    @Override
    public BrandDTO deleteById(Long id) {
        return null;
    }

    @Override
    public Brand findModelById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
    }

    private void validateUniqueField(String name, Long existingId) throws FieldUniqueException {
        Optional<Brand> brand = brandRepository.findByName(name);

        if(brand.isPresent() && !brand.get().getId().equals(existingId)) {
            throw new FieldUniqueException("Brand already exists");
        }
    }
}
