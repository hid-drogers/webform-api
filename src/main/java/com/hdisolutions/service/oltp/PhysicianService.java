package com.hdisolutions.service.oltp;

import java.util.List;

import com.hdisolutions.model.domain.oltp.Physician;

public interface PhysicianService {
	
	public List<Physician> getPhysicians(String clientName, String npi);
}
