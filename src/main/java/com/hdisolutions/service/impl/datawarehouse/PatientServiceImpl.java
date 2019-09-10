package com.hdisolutions.service.impl.datawarehouse;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hdisolutions.dao.datawarehouse.PatientDAO;
import com.hdisolutions.model.domain.datawarehouse.Patient;
import com.hdisolutions.service.datawarehouse.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	@Inject
	private PatientDAO patientDAO;

	@Override
	public List<Patient> getPatientsByMemberIdAndOtherCriteria(String clientName, String memberId, String firstName,
													           String lastName, Date dateOfBirth) {
		
		return patientDAO.getPatientsByMemberIdAndOtherCriteria(clientName, memberId, firstName, lastName, dateOfBirth);
	}

	@Override
	public List<Patient> getPatientsByAltMemberIdAndOtherCriteria(String clientName, String altMemberId, String firstName,
														          String lastName, Date dateOfBirth) {
		
		return patientDAO.getPatientsByAltMemberIdAndOtherCriteria(clientName, altMemberId, firstName, lastName, dateOfBirth);
	} 
}
