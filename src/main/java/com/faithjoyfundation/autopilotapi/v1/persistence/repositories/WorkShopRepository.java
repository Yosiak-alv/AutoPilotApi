package com.faithjoyfundation.autopilotapi.v1.persistence.repositories;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.WorkShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkShopRepository extends JpaRepository<WorkShop, Long> {
    @Query("SELECT w FROM WorkShop w " +
            "WHERE (:search IS NULL OR w.name LIKE %:search% OR w.email LIKE %:search% OR w.phone LIKE %:search%) " +
            "AND (:municipalityId IS NULL OR w.municipality.id = :municipalityId) " +
            "AND (:departmentId IS NULL OR w.municipality.department.id = :departmentId) " +
            "ORDER BY w.updatedAt DESC")
    Page<WorkShop> findAllBySearch(
            @Param("search") String search,
            @Param("municipalityId") Long municipalityId,
            @Param("departmentId") Long departmentId,
            Pageable pageable
    );

    Optional<WorkShop> findByEmail(String email);

    Optional<WorkShop> findByPhone(String phone);
}
