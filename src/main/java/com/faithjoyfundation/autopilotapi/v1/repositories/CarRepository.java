package com.faithjoyfundation.autopilotapi.v1.repositories;

import com.faithjoyfundation.autopilotapi.v1.models.Branch;
import com.faithjoyfundation.autopilotapi.v1.models.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c ORDER BY c.id ASC")
    Page<Car> findAllOrderedById(Pageable pageable);

    @Query("SELECT c FROM Car c WHERE c.plates LIKE %:search% OR c.VIN LIKE %:search% OR c.model.name LIKE %:search% OR c.model.brand.name LIKE %:search% OR CAST(c.year AS string) = :search ORDER BY c.id ASC")
    Page<Car> findAllBySearch(String search, Pageable pageable);

    //for validation purposes

    Optional<Car> findByPlatesAndVIN(String plates, String VIN);

    Optional<Car> findByPlates(String plates);

    Optional<Car> findByVIN(String VIN);

}
