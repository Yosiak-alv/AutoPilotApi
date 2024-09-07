package com.faithjoyfundation.autopilotapi.v1.persistence.repositories;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    //Optional<Branch> findById(Long id);
}
