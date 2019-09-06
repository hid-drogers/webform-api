package com.hdisolutions.service.impl.datawarehouse;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hdisolutions.dao.datawarehouse.PatientDAO;
import com.hdisolutions.service.datawarehouse.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	@Inject
	private PatientDAO patientDAO;

	@Override
	public Integer getPatientCountByMemberIdAndOtherCriteria(String clientName, String memberId, String firstName,
													         String lastName, Date dateOfBirth) {
		
		return patientDAO.getPatientCountByMemberIdAndOtherCriteria(clientName, memberId, firstName, lastName, dateOfBirth);
	}

	@Override
	public Integer getPatientCountByAltMemberIdAndOtherCriteria(String clientName, String altMemberId, String firstName,
														        String lastName, Date dateOfBirth) {
		
		return patientDAO.getPatientCountByMemberIdAndOtherCriteria(clientName, altMemberId, firstName, lastName, dateOfBirth);
	} 
}
