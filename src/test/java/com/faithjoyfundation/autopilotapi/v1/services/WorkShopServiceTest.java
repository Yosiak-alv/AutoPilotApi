package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ConflictException;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.workshop.WorkShopDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.workshop.WorkShopListDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.workshop.WorkShopRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Department;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Municipality;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Repair;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.WorkShop;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.MunicipalityRepository;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.WorkShopRepository;
import com.faithjoyfundation.autopilotapi.v1.services.impl.WorkShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkShopServiceTest {
    @Mock
    private WorkShopRepository workShopRepository;

    @Mock
    private MunicipalityRepository municipalityRepository;

    @InjectMocks
    private WorkShopServiceImpl workShopService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllBySearch() {
        // Given
        String search = "workshop";
        Long municipalityId = 1L;
        Long departmentId = 1L;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        WorkShop workShop = new WorkShop();
        workShop.setId(1L);
        workShop.setName("WorkShop");
        workShop.setEmail("workshop@email.com");
        workShop.setPhone("123456789");
        workShop.setMunicipality(new Municipality(1L, "municiplity 1", new Department(),null,null));
        Page<WorkShop> workShopPage = new PageImpl<>(Arrays.asList(workShop));

        when(workShopRepository.findAllBySearch(search, municipalityId, departmentId, pageable))
                .thenReturn(workShopPage);

        // When
        PaginatedResponse<WorkShopListDTO> result = workShopService.findAllBySearch(search, municipalityId, departmentId, page, size);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getData().size());
        verify(workShopRepository, times(1)).findAllBySearch(search, municipalityId, departmentId, pageable);
    }

    @Test
    void testFindDTOById() {
        // Given
        WorkShop workShop = new WorkShop();
        workShop.setId(1L);
        workShop.setName("WorkShop");
        workShop.setEmail("workshop@email.com");
        workShop.setPhone("123456789");
        workShop.setMunicipality(new Municipality(1L, "municipality 1", new Department(),null,null));
        Page<WorkShop> workShopPage = new PageImpl<>(Arrays.asList(workShop));

        when(workShopRepository.findById(1L)).thenReturn(Optional.of(workShop));

        // When
        WorkShopDTO result = workShopService.findDTOById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(workShopRepository, times(1)).findById(1L);
    }

    @Test
    void testCreate() {
        // Given
        WorkShopRequest request = new WorkShopRequest();
        request.setName("New WorkShop");
        request.setEmail("workshop@example.com");
        request.setPhone("123456789");
        request.setAddress("123 Main St");
        request.setMunicipalityId(1L);

        Municipality municipality = new Municipality();
        municipality.setId(1L);
        municipality.setName("municipality 1");
        municipality.setDepartment(new Department());

        when(municipalityRepository.findById(1L)).thenReturn(Optional.of(municipality));
        when(workShopRepository.save(any(WorkShop.class))).thenAnswer(invocation -> {
            WorkShop savedWorkShop = invocation.getArgument(0);
            savedWorkShop.setId(1L);
            return savedWorkShop;
        });

        // When
        WorkShopDTO result = workShopService.create(request);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New WorkShop", result.getName());
        verify(municipalityRepository, times(1)).findById(1L);
        verify(workShopRepository, times(1)).save(any(WorkShop.class));
    }

    @Test
    void testDeleteWorkShopWithoutRepairs() {
        // Given
        WorkShop workShop = new WorkShop();
        workShop.setId(1L);
        workShop.setRepairs(new HashSet<>()); // No repairs associated

        when(workShopRepository.findById(1L)).thenReturn(Optional.of(workShop));

        // When
        boolean result = workShopService.delete(1L);

        // Then
        assertTrue(result);
        verify(workShopRepository, times(1)).findById(1L);
        verify(workShopRepository, times(1)).delete(workShop);
    }

    @Test
    void testDeleteWorkShopWithRepairs() {
        // Given
        WorkShop workShop = new WorkShop();
        workShop.setId(1L);

        Set<Repair> repairs = new HashSet<>();
        repairs.add(new Repair());
        workShop.setRepairs(repairs);

        when(workShopRepository.findById(1L)).thenReturn(Optional.of(workShop));

        // When / Then
        ConflictException exception = assertThrows(ConflictException.class, () -> {
            workShopService.delete(1L);
        });

        assertEquals("WorkShop has repairs associated with it and cannot be deleted.", exception.getMessage());
        verify(workShopRepository, times(1)).findById(1L);
        verify(workShopRepository, times(0)).delete(workShop); // Should not be called
    }
}