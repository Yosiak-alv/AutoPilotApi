package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.BrandDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.BrandListDTO;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.brand_managment.BrandRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ConflictException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Brand;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.BrandRepository;
import com.faithjoyfundation.autopilotapi.v1.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public PaginatedResponse<BrandListDTO> findAllBySearch(String search, int page, int size) {
        validatePageNumberAndSize(page, size);
        //Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Pageable pageable = PageRequest.of(page, size);
        return new PaginatedResponse<>(brandRepository.findByNameContainingOrderById(search, pageable).map(BrandListDTO::new));
    }

    @Override
    public BrandDTO findDTOById(Long id) {
        return new BrandDTO(findModelById(id));
    }

    @Override
    public Brand findModelById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Brand not found with id " + id));
    }

    @Override
    public BrandDTO create(BrandRequest brandRequest) {
        return saveOrUpdate(new Brand(), brandRequest, null);
    }

    @Override
    public BrandDTO update(Long id, BrandRequest brandRequest) {
        return saveOrUpdate(this.findModelById(id), brandRequest, id);
    }

    @Override
    public boolean delete(Long id) {
        Brand brand = this.findModelById(id);

        brand.getModels().forEach(model -> {
            if (!model.getCars().isEmpty()) {
                throw new ConflictException("Brand with id: " + id + ", cannot be deleted because it has associated models with cars.");
            }
        });

        brandRepository.delete(brand);
        return true;
    }

    private BrandDTO saveOrUpdate(Brand brand, BrandRequest brandRequest, Long existingId) {
       validateUniqueName(brandRequest.getName(), existingId);
       brand.setName(brandRequest.getName());
       brand = brandRepository.save(brand);
       return new BrandDTO(brand);
    }

    private void validateUniqueName(String name, Long existingId) {
        Optional<Brand> brand = brandRepository.findByName(name);
        if(brand.isPresent() && !brand.get().getId().equals(existingId)) {
            throw new BadRequestException("Brand with name " + name + " already exists.");
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
