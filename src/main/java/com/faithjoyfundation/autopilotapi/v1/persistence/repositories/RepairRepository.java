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
    @Query("SELECT r FROM Repair r " +
            "WHERE (:workshopId IS NULL OR r.workshop.id = :workshopId) " +
            "AND (:statusId IS NULL OR r.repairStatus.id = :statusId) " +
            "AND r.car.id = :carId")
    Page<Repair> findAllBySearch(
            @Param("carId") Long carId,
            @Param("workshopId") Long workshopId,
            @Param("statusId") Long statusId,
            Pageable pageable
    );
}
