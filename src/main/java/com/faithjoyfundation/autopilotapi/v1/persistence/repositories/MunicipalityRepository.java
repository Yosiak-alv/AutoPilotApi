package com.faithjoyfundation.autopilotapi.v1.persistence.repositories;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality,Long> {

}
