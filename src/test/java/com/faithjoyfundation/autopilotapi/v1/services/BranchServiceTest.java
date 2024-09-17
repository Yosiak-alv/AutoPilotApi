package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ConflictException;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.branch.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.branch.BranchListDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.branch.BranchRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Branch;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Car;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Department;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Municipality;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.BranchRepository;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.MunicipalityRepository;
import com.faithjoyfundation.autopilotapi.v1.services.impl.BranchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private MunicipalityRepository municipalityRepository;

    @InjectMocks
    private BranchServiceImpl branchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllBySearch() {
        // Given
        String search = "branch";
        Long municipalityId = 1L;
        Long departmentId = 1L;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Branch branch = new Branch();
        branch.setId(1L);
        branch.setName("Branch");
        branch.setEmail("branch@email.com");
        branch.setPhone("123456789");
        branch.setAddress("123 Main St");
        branch.setMain(true);
        branch.setMunicipality( new Municipality(1L, "municiplity 1", new Department(),null,null));
        Page<Branch> branchPage = new PageImpl<>(Arrays.asList(branch));

        when(branchRepository.findAllBySearch(search, municipalityId, departmentId, pageable))
                .thenReturn(branchPage);

        // When
        PaginatedResponse<BranchListDTO> result = branchService.findAllBySearch(search, municipalityId, departmentId, page, size);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getData().size());
        verify(branchRepository, times(1)).findAllBySearch(search, municipalityId, departmentId, pageable);
    }

    @Test
    void testFindDTOById() {
        Branch branch = new Branch();
        branch.setId(1L);
        branch.setName("Branch");
        branch.setEmail("branch@email.com");
        branch.setPhone("123456789");
        branch.setAddress("123 Main St");
        branch.setMain(true);
        branch.setMunicipality( new Municipality(1L, "municipality 1", new Department(),null,null));
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));

        // When
        BranchDTO result = branchService.findDTOById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(branchRepository, times(1)).findById(1L);
    }

    @Test
    void testCreate() {
        // Given
        BranchRequest request = new BranchRequest();
        request.setName("New Branch");
        request.setEmail("branch@example.com");
        request.setPhone("123456789");
        request.setAddress("123 Main St");
        request.setMunicipalityId(1L);

        Municipality municipality = new Municipality();
        municipality.setId(1L);
        municipality.setName("municipality 1");
        municipality.setDepartment(new Department());

        when(municipalityRepository.findById(1L)).thenReturn(Optional.of(municipality));
        when(branchRepository.save(any(Branch.class))).thenAnswer(invocation -> {
            Branch savedBranch = invocation.getArgument(0);
            savedBranch.setId(1L);
            return savedBranch;
        });

        // When
        BranchDTO result = branchService.create(request);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Branch", result.getName());
        verify(municipalityRepository, times(1)).findById(1L);
    }

    @Test
    void testDelete() {
        Branch branch = new Branch();
        branch.setId(1L);
        branch.setCars(new HashSet<>()); // No cars associated

        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));

        // When
        boolean result = branchService.delete(1L);

        // Then
        assertTrue(result);
        verify(branchRepository, times(1)).findById(1L);
        verify(branchRepository, times(1)).delete(branch);
    }

    @Test
    void testDeleteWithCars() {
        Branch branch = new Branch();
        branch.setId(1L);

        // Mocking the presence of cars associated with the branch
        Set<Car> cars = new HashSet<>();
        cars.add(new Car()); // Adding a car to the set
        branch.setCars(cars); // Branch now has associated cars

        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));

        // When / Then
        ConflictException exception = assertThrows(ConflictException.class, () -> {
            branchService.delete(1L);
        });

        assertEquals("Branch cannot be deleted because it has cars associated with it.", exception.getMessage());
        verify(branchRepository, times(1)).findById(1L);
        verify(branchRepository, times(0)).delete(branch); // Should not be called
    }
}