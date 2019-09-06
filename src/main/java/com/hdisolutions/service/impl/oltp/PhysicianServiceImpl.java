package com.hdisolutions.service.impl.oltp;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hdisolutions.dao.oltp.PhysicianDAO;
import com.hdisolutions.model.domain.oltp.Physician;
import com.hdisolutions.service.oltp.PhysicianService;

@Service
public class PhysicianServiceImpl implements PhysicianService {

	@Inject
	private PhysicianDAO physicianDAO; 
	
	@Override
	public List<Physician> getPhysicians(String clientName, String npi) {
		return physicianDAO.getPhysicians(clientName, npi);
	}
}
