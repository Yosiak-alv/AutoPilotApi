package com.faithjoyfundation.autopilotapi.v1.persistence.repositories;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
