package com.faithjoyfundation.autopilotapi.v1.repositories;

import com.faithjoyfundation.autopilotapi.v1.models.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
}
