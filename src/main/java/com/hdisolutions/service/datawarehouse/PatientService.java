package com.hdisolutions.service.datawarehouse;

import java.util.Date;
import java.util.List;

import com.hdisolutions.model.domain.datawarehouse.Patient;

public interface PatientService {
	
	public List<Patient> getPatientsByMemberIdAndOtherCriteria(String clientName, String memberId, String firstName,
													           String lastName, Date dateOfBirth);
	
	public List<Patient> getPatientsByAltMemberIdAndOtherCriteria(String clientName, String altMemberId, String firstName,
		    										              String lastName, Date dateOfBirth);	
}
