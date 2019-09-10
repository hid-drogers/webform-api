package com.hdisolutions.dao.datawarehouse;

import java.util.Date;
import java.util.List;

import com.hdisolutions.model.domain.datawarehouse.Patient;

public interface PatientDAO {
	
	List<Patient> getPatientsByMemberIdAndOtherCriteria(String clientName, String memberId, String firstName,
	        									        String lastName, Date dateOfBirth);
	
	List<Patient> getPatientsByAltMemberIdAndOtherCriteria(String clientName, String memberId, String firstName,
			 										       String lastName, Date dateOfBirth);	
}
