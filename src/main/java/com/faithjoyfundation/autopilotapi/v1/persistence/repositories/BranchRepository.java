package com.faithjoyfundation.autopilotapi.v1.persistence.repositories;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Branch;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.WorkShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {


    @Query("SELECT b FROM Branch b " +
            "WHERE (:search IS NULL OR b.name LIKE %:search% OR b.email LIKE %:search% OR b.phone LIKE %:search%) " +
            "AND (:municipalityId IS NULL OR b.municipality.id = :municipalityId) " +
            "AND (:departmentId IS NULL OR b.municipality.department.id = :departmentId) " +
            "ORDER BY b.updatedAt DESC")
    Page<Branch> findAllBySearch(
            @Param("search") String search,
            @Param("municipalityId") Long municipalityId,
            @Param("departmentId") Long departmentId,
            Pageable pageable
    );
    Optional<Branch> findByEmail(String email);

    Optional<Branch> findByPhone(String phone);
}
