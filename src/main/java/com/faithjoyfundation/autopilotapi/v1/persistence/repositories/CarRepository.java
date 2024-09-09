package com.faithjoyfundation.autopilotapi.v1.persistence.repositories;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c WHERE c.plates LIKE %:search% OR c.branch.name LIKE %:search% OR c.model.name LIKE %:search% OR c.model.brand.name LIKE %:search% OR CAST(c.year AS string) = :search ORDER BY c.id ASC")
    Page<Car> findAllBySearch(String search, Pageable pageable);

    Optional<Car> findByPlates(String plates);

    Optional<Car> findByVIN(String VIN);
}
