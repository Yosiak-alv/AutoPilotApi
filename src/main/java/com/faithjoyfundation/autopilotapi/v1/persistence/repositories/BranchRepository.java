package com.faithjoyfundation.autopilotapi.v1.persistence.repositories;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    @Query("SELECT b FROM Branch b WHERE b.name LIKE %:search% OR b.email LIKE %:search% OR b.phone LIKE %:search% OR b.municipality.name LIKE %:search% OR b.municipality.department.name LIKE %:search% ")
    Page<Branch> findAllBySearch(String search, Pageable pageable);

    Optional<Branch> findByEmail(String email);

    Optional<Branch> findByPhone(String phone);
}
