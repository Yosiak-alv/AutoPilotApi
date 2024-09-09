package com.faithjoyfundation.autopilotapi.v1.persistence.repositories;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    @Query("SELECT m FROM Model m  WHERE (m.name LIKE %:search% ) AND m.brand.id = :brandId")
    Page<Model> findAllBySearchAndBrand(@Param("brandId") Long brandId, @Param("search") String search, Pageable pageable);

    Optional<Model> findByName(String name);
}
