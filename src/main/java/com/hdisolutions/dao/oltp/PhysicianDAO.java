package com.hdisolutions.dao.oltp;

import java.util.List;

import com.hdisolutions.model.domain.oltp.Physician;

public interface PhysicianDAO {
	
	List<Physician> getPhysicians(String clientName, String npi);
}
