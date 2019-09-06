package com.hdisolutions.dao.impl.datawarehouse;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.hdisolutions.dao.BaseDAO;
import com.hdisolutions.dao.DAOConstants;
import com.hdisolutions.dao.datawarehouse.PatientDAO;
import com.hdisolutions.repository.datawarehouse.PatientRepository;

@Component
public class PatientDAOImpl extends BaseDAO implements PatientDAO {

	@Inject
	private PatientRepository patientRepo;

	@Override
	public Integer getPatientCountByMemberIdAndOtherCriteria(String clientName, String memberId, String firstName,
												             String lastName, Date dateOfBirth) {
		
		setDataSourceLookupInfo(clientName);
		
		return patientRepo.getCountByMemberIdAndOtherCriteria(memberId, firstName.toUpperCase(), lastName.toUpperCase(), dateOfBirth);
	}
	
	private void setDataSourceLookupInfo(String clientName) {
		setDataSourceLookupInfo(DAOConstants.DB_TYPE_DW, clientName);
	}

	@Override
	public Integer getPatientCountByAltMemberIdAndOtherCriteria(String clientName, String memberId, String firstName,
														        String lastName, Date dateOfBirth) {
		
		setDataSourceLookupInfo(clientName);
		
		return patientRepo.getCountByAltMemberIdAndOtherCriteria(memberId, firstName.toUpperCase(), lastName.toUpperCase(), dateOfBirth);
	}
}
