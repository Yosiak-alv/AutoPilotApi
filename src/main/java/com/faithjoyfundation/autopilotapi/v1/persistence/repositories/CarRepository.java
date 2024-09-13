package com.faithjoyfundation.autopilotapi.v1.persistence.repositories;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c " +
            "WHERE (:search IS NULL OR c.plates LIKE %:search% OR c.VIN LIKE %:search% OR CAST(c.year AS string) = :search ) " +
            "AND (:branchId IS NULL OR c.branch.id = :branchId) " +
            "AND (:brandId IS NULL OR c.model.brand.id = :brandId) " +
            "AND (:modelId IS NULL OR c.model.id = :modelId) " +
            "ORDER BY c.updatedAt DESC")
    Page<Car> findAllBySearch(
            @Param("search") String search,
            @Param("branchId") Long branchId,
            @Param("brandId") Long brandId,
            @Param("modelId") Long modelId,
            Pageable pageable
    );

    Optional<Car> findByPlates(String plates);

    Optional<Car> findByVIN(String VIN);
}
