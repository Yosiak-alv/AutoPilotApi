package com.faithjoyfundation.autopilotapi.v1.repositories;

import com.faithjoyfundation.autopilotapi.v1.models.WorkShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkShopRepository extends JpaRepository<WorkShop, Long> {
    @Query("SELECT w FROM WorkShop w ORDER BY w.id ASC")
    Page<WorkShop> findAllOrderedById(Pageable pageable);

    @Query("SELECT w FROM WorkShop w WHERE w.name LIKE %:search% OR w.email LIKE %:search% OR w.phone LIKE %:search% OR w.municipality.name LIKE %:search% OR w.municipality.department.name LIKE %:search% ORDER BY w.id ASC")
    Page<WorkShop> findAllBySearch(@Param("search") String search, Pageable pageable);

    //for validation purposes
    Optional<WorkShop> findByEmailAndPhone(String email, String phone);

    Optional<WorkShop> findByEmail(String email);

    Optional<WorkShop> findByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}

