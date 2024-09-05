package com.faithjoyfundation.autopilotapi.v1.repositories;

import com.faithjoyfundation.autopilotapi.v1.models.RepairStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepairStatusRepository extends JpaRepository<RepairStatus, Long> {
    @Query("SELECT r FROM RepairStatus r ORDER BY r.id ASC")
    List<RepairStatus> findAllOrderedById();

}
