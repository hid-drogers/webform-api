package com.hdisolutions.repository.oltp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hdisolutions.model.domain.oltp.Physician;

@Repository
public interface PhysicianRepository extends CrudRepository<Physician, Integer> {
	
	List<Physician> findPhysiciansByUcNpi(@Param("npi") String npi);
}
