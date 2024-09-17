package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.enums.CarColor;
import com.faithjoyfundation.autopilotapi.v1.common.enums.RepairStatusType;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair.RepairDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair.RepairListDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair.RepairRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair.UpdateRepairStatusRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.*;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.RepairDetailRepository;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.RepairRepository;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.RepairStatusRepository;
import com.faithjoyfundation.autopilotapi.v1.services.impl.RepairServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RepairServiceTest {
    @Mock
    private RepairRepository repairRepository;

    @Mock
    private RepairDetailRepository repairDetailRepository;

    @Mock
    private RepairStatusRepository repairStatusRepository;

    @Mock
    private CarService carService;

    @Mock
    private WorkShopService workShopService;

    @InjectMocks
    private RepairServiceImpl repairService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllBySearch() {
        // Given
        Long carId = 1L;
        Long workshopId = 1L;
        Long repairStatusId = 1L;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Car car = new Car();
        car.setId(1L);
        car.setPlates("ABC123");
        car.setVIN("1CADVRDACACECEC");
        car.setMotorID("12345623EF32FEQ");
        car.setYear(2020);
        car.setColor(CarColor.BLACK.name());
        car.setModel(new Model(1L, "Model",new Brand(), new HashSet<>()));
        car.setBranch(new Branch(1L, "Branch", "BRANCH@EXAMPLE.COM", "123456789",
                true, "address example" ,null,
                null, new Municipality(1L, "municiplity 1", new Department(),null,null), new HashSet<>()));

        WorkShop workShop = new WorkShop();
        workShop.setId(1L);
        workShop.setName("WorkShop");
        workShop.setEmail("workshop@email.com");
        workShop.setPhone("123456789");
        workShop.setMunicipality(new Municipality(1L, "municipality 1", new Department(),null,null));

        RepairStatus repairStatus = new RepairStatus();
        repairStatus.setId(1L);
        repairStatus.setName(RepairStatusType.PENDING.name());

        Repair repair = new Repair();
        repair.setId(1L);
        repair.setId(1L);
        repair.setTotal(BigDecimal.valueOf(100));
        repair.setCar(car);
        repair.setWorkshop(workShop);
        repair.setRepairStatus(repairStatus);
        repair.setRepairDetails(new HashSet<>());
        Page<Repair> repairPage = new PageImpl<>(Arrays.asList(repair));

        when(repairRepository.findAllBySearch(carId, workshopId, repairStatusId, pageable))
                .thenReturn(repairPage);

        // When
        PaginatedResponse<RepairListDTO> result = repairService.findAllBySearch(carId, workshopId, repairStatusId, page, size);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getData().size());
        verify(repairRepository, times(1)).findAllBySearch(carId, workshopId, repairStatusId, pageable);
    }

    @Test
    void testFindDTOById() {
        // Given
        Car car = new Car();
        car.setId(1L);
        car.setPlates("ABC123");
        car.setVIN("1CADVRDACACECEC");
        car.setMotorID("12345623EF32FEQ");
        car.setYear(2020);
        car.setColor(CarColor.BLACK.name());
        car.setModel(new Model(1L, "Model",new Brand(), new HashSet<>()));
        car.setBranch(new Branch(1L, "Branch", "BRANCH@EXAMPLE.COM", "123456789",
                true, "address example" ,null,
                null, new Municipality(1L, "municiplity 1", new Department(),null,null), new HashSet<>()));

        WorkShop workShop = new WorkShop();
        workShop.setId(1L);
        workShop.setName("WorkShop");
        workShop.setEmail("workshop@email.com");
        workShop.setPhone("123456789");
        workShop.setMunicipality(new Municipality(1L, "municipality 1", new Department(),null,null));

        RepairStatus repairStatus = new RepairStatus();
        repairStatus.setId(1L);
        repairStatus.setName(RepairStatusType.PENDING.name());

        Repair repair = new Repair();
        repair.setId(1L);
        repair.setId(1L);
        repair.setTotal(BigDecimal.valueOf(100));
        repair.setCar(car);
        repair.setWorkshop(workShop);
        repair.setRepairStatus(repairStatus);
        repair.setRepairDetails(new HashSet<>());
        when(repairRepository.findById(1L)).thenReturn(Optional.of(repair));

        // When
        RepairDTO result = repairService.findDTOById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repairRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateRepair() {
        // Given
        RepairRequest request = new RepairRequest();
        request.setCarId(1L);
        request.setWorkshopId(1L);
        request.setRepairStatusId(1L);

        Car car = new Car();
        car.setId(1L);
        car.setPlates("ABC123");
        car.setVIN("1CADVRDACACECEC");
        car.setMotorID("12345623EF32FEQ");
        car.setYear(2020);
        car.setColor(CarColor.BLACK.name());
        car.setModel(new Model(1L, "Model",new Brand(), new HashSet<>()));
        car.setBranch(new Branch(1L, "Branch", "BRANCH@EXAMPLE.COM", "123456789",
                true, "address example" ,null,
                null, new Municipality(1L, "municiplity 1", new Department(),null,null), new HashSet<>()));

        WorkShop workShop = new WorkShop();
        workShop.setId(1L);
        workShop.setName("WorkShop");
        workShop.setEmail("workshop@email.com");
        workShop.setPhone("123456789");
        workShop.setMunicipality(new Municipality(1L, "municipality 1", new Department(),null,null));

        RepairStatus repairStatus = new RepairStatus();
        repairStatus.setId(1L);
        repairStatus.setName(RepairStatusType.PENDING.name());

        when(carService.findModelById(1L)).thenReturn(car);
        when(workShopService.findModelById(1L)).thenReturn(workShop);
        when(repairStatusRepository.findById(1L)).thenReturn(Optional.of(repairStatus));
        when(repairRepository.save(any(Repair.class))).thenAnswer(invocation -> {
            Repair savedRepair = invocation.getArgument(0);
            savedRepair.setId(1L);
            return savedRepair;
        });

        // When
        RepairDTO result = repairService.create(request);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(carService, times(1)).findModelById(1L);
        verify(workShopService, times(1)).findModelById(1L);
        verify(repairStatusRepository, times(1)).findById(1L);
        verify(repairRepository, times(1)).save(any(Repair.class));
    }

    @Test
    void testDeleteRepairWithoutCompletedOrCanceledStatus() {
        // Given
        Repair repair = new Repair();
        repair.setId(1L);
        repair.setRepairStatus(new RepairStatus(1L, RepairStatusType.PENDING.name(), null));

        when(repairRepository.findById(1L)).thenReturn(Optional.of(repair));

        // When
        boolean result = repairService.delete(1L);

        // Then
        assertTrue(result);
        verify(repairDetailRepository, times(1)).deleteAll(repair.getRepairDetails());
        verify(repairRepository, times(1)).delete(repair);
    }

    @Test
    void testDeleteRepairWithCompletedStatus() {
        // Given
        Repair repair = new Repair();
        repair.setId(1L);
        repair.setRepairStatus(new RepairStatus(1L, RepairStatusType.COMPLETED.name(), null));

        when(repairRepository.findById(1L)).thenReturn(Optional.of(repair));

        // When / Then
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            repairService.delete(1L);
        });

        assertEquals("You cannot delete a repair that is already completed or canceled", exception.getMessage());
        verify(repairRepository, times(1)).findById(1L);
        verify(repairRepository, times(0)).delete(repair); // Should not be called
    }
}