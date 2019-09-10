package com.hdisolutions.dao.impl.datawarehouse;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.hdisolutions.dao.BaseDAO;
import com.hdisolutions.dao.DAOConstants;
import com.hdisolutions.dao.datawarehouse.PatientDAO;
import com.hdisolutions.model.domain.datawarehouse.Patient;
import com.hdisolutions.repository.datawarehouse.PatientRepository;

@Component
public class PatientDAOImpl extends BaseDAO implements PatientDAO {

	@Inject
	private PatientRepository patientRepo;

	@Override
	public List<Patient> getPatientsByMemberIdAndOtherCriteria(String clientName, String memberId, String firstName,
												               String lastName, Date dateOfBirth) {
		
		setDataSourceLookupInfo(clientName);
		
		return patientRepo.findAllByMemberIdAndOtherCriteria(memberId, firstName.toUpperCase(), lastName.toUpperCase(), dateOfBirth);
	}
	
	private void setDataSourceLookupInfo(String clientName) {
		setDataSourceLookupInfo(DAOConstants.DB_TYPE_DW, clientName);
	}

	@Override
	public List<Patient> getPatientsByAltMemberIdAndOtherCriteria(String clientName, String memberId, String firstName,
														          String lastName, Date dateOfBirth) {
		
		setDataSourceLookupInfo(clientName);
		
		return patientRepo.findAllByAltMemberIdAndOtherCriteria(memberId, firstName.toUpperCase(), lastName.toUpperCase(), dateOfBirth);
	}
}
