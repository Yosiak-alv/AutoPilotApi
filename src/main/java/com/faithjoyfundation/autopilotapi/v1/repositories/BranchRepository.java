package com.faithjoyfundation.autopilotapi.v1.repositories;

import com.faithjoyfundation.autopilotapi.v1.models.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    @Query("SELECT b FROM Branch b ORDER BY b.id ASC")
    Page<Branch> findAllOrderedById(Pageable pageable);

    @Query("SELECT b FROM Branch b WHERE b.name LIKE %:search% OR b.email LIKE %:search% OR b.phone LIKE %:search% OR b.municipality.name LIKE %:search% OR b.municipality.department.name LIKE %:search% ORDER BY b.id ASC")
    Page<Branch> findAllBySearch(String search, Pageable pageable);

    //for validation purposes
    Optional<Branch> findByEmailAndPhone(String email, String phone);

    Optional<Branch> findByEmail(String email);

    Optional<Branch> findByPhone(String phone);
}
