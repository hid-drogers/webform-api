package com.hdisolutions.dao.impl.oltp;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.hdisolutions.dao.BaseDAO;
import com.hdisolutions.dao.DAOConstants;
import com.hdisolutions.dao.oltp.PhysicianDAO;
import com.hdisolutions.model.domain.oltp.Physician;
import com.hdisolutions.repository.oltp.PhysicianRepository;

@Component
public class PhysicianDAOImpl extends BaseDAO implements PhysicianDAO {

	@Inject
	private PhysicianRepository physicianRepo;
	
	@Override
	public List<Physician> getPhysicians(String clientName, String npi) {

		setDataSourceLookupInfo(clientName);
		
		return physicianRepo.findPhysiciansByUcNpi(npi.toUpperCase());
	}

	private void setDataSourceLookupInfo(String clientName) {
		setDataSourceLookupInfo(DAOConstants.DB_TYPE_OLTP, clientName);
	}
}
