package com.hdisolutions.dao.datawarehouse;

import java.util.Date;

public interface PatientDAO {
	
	Integer getPatientCountByMemberIdAndOtherCriteria(String clientName, String memberId, String firstName,
	        									      String lastName, Date dateOfBirth);
	
	Integer getPatientCountByAltMemberIdAndOtherCriteria(String clientName, String memberId, String firstName,
			 										     String lastName, Date dateOfBirth);	
}
