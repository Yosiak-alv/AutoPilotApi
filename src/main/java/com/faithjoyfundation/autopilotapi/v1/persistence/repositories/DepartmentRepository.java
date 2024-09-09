package com.faithjoyfundation.autopilotapi.v1.persistence.repositories;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("SELECT d FROM Department d ORDER BY d.id ASC")
    List<Department> findAllOrderById();
}
