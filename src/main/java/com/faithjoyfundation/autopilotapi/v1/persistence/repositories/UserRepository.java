package com.faithjoyfundation.autopilotapi.v1.persistence.repositories;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN u.roles r  WHERE u.email LIKE %:search% " +
            "OR u.name LIKE %:search%  OR r.name LIKE %:search%")
    Page<User> findAllBySearch(@Param("search") String search, Pageable pageable);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
