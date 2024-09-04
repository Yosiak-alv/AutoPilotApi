package com.faithjoyfundation.autopilotapi.v1.repositories;

import com.faithjoyfundation.autopilotapi.v1.models.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("SELECT b FROM Brand b ORDER BY b.id ASC")
    Page<Brand> findAllOrderedById(Pageable pageable);

    @Query("SELECT b FROM Brand b WHERE b.name LIKE %:search% ORDER BY b.id ASC")
    Page<Brand> findByNameContainingOrderById(@Param("search") String search, Pageable pageable);

    boolean existsByName(String name);

    Optional<Brand> findByName(String name);
}
