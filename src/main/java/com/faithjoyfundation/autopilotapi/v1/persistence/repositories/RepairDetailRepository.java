package com.faithjoyfundation.autopilotapi.v1.persistence.repositories;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.RepairDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RepairDetailRepository extends JpaRepository<RepairDetail, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM RepairDetail rd WHERE rd.repair.id = :repairId")
    void deleteByRepairId(@Param("repairId") Long repairId);
}
