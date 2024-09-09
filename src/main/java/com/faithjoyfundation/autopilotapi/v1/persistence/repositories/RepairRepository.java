package com.faithjoyfundation.autopilotapi.v1.persistence.repositories;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Repair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {
    @Query("SELECT r FROM Repair r WHERE (r.repairStatus.name LIKE %:search% " +
            "OR r.workshop.name LIKE %:search% OR r.workshop.municipality.name LIKE %:search% " +
            "OR r.workshop.municipality.department.name LIKE %:search% OR r.workshop.phone LIKE %:search% " +
            "OR r.workshop.email LIKE %:search% OR CAST(r.total AS string) = :search )AND r.car.id = :carId")
    Page<Repair> findAllBySearch(@Param("carId") Long carId, String search, Pageable pageable);
}
