package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.enums.CarColor;
import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ConflictException;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.car.CarDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.car.CarListDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.car.CarRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.*;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.CarRepository;
import com.faithjoyfundation.autopilotapi.v1.services.impl.CarServiceImpl;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceTest {
    @Mock
    private CarRepository carRepository;

    @Mock
    private ModelService modelService;

    @Mock
    private BranchService branchService;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllBySearch() {
        // Given
        String search = "ABC123";
        Long branchId = 1L;
        Long brandId = 1L;
        Long modelId = 1L;
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
        Page<Car> carPage = new PageImpl<>(Arrays.asList(car));

        when(carRepository.findAllBySearch(search, branchId, brandId, modelId, pageable))
                .thenReturn(carPage);

        // When
        PaginatedResponse<CarListDTO> result = carService.findAllBySearch(search, branchId, brandId, modelId, page, size);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getData().size());
        verify(carRepository, times(1)).findAllBySearch(search, branchId, brandId, modelId, pageable);
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


        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        // When
        CarDTO result = carService.findDTOById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(carRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateCar() {
        // Given
        CarRequest request = new CarRequest();
        request.setPlates("ABC123");
        request.setVIN("1HGCM82633A123456");
        request.setColor("Red");
        request.setYear(2020);
        request.setMileage(131000);
        request.setMotorId("82633A123456");
        request.setModelId(1L);
        request.setBranchId(1L);

        Model model = new Model();
        model.setId(1L);
        model.setName("Model");
        model.setBrand(new Brand());

        Branch branch = new Branch();
        branch.setId(1L);
        branch.setName("Branch");
        branch.setEmail("branch@email.com");
        branch.setPhone("123456789");
        branch.setAddress("123 Main St");
        branch.setMain(true);
        branch.setMunicipality( new Municipality(1L, "municipality 1", new Department(),null,null));

        when(modelService.findModelById(1L)).thenReturn(model);
        when(branchService.findModelById(1L)).thenReturn(branch);
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> {
            Car savedCar = invocation.getArgument(0);
            savedCar.setId(1L);
            return savedCar;
        });

        // When
        CarDTO result = carService.create(request);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ABC123", result.getPlates());
        verify(modelService, times(1)).findModelById(1L);
        verify(branchService, times(1)).findModelById(1L);
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void testDeleteCarWithoutRepairs() {
        // Given
        Car car = new Car();
        car.setId(1L);
        car.setRepairs(new HashSet<>()); // No repairs associated

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        // When
        boolean result = carService.delete(1L);

        // Then
        assertTrue(result);
        verify(carRepository, times(1)).findById(1L);
        verify(carRepository, times(1)).delete(car);
    }

    @Test
    void testDeleteCarWithRepairs() {
        // Given
        Car car = new Car();
        car.setId(1L);

        // Simulating repairs associated with the car
        Set<Repair> repairs = new HashSet<>();
        repairs.add(new Repair());
        car.setRepairs(repairs);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        // When / Then
        ConflictException exception = assertThrows(ConflictException.class, () -> {
            carService.delete(1L);
        });

        assertEquals("Car has repairs associated with it and cannot be deleted.", exception.getMessage());
        verify(carRepository, times(1)).findById(1L);
        verify(carRepository, times(0)).delete(car); // Should not be called
    }
}