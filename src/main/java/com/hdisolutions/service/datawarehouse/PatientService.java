package com.hdisolutions.service.datawarehouse;

import java.util.Date;

public interface PatientService {
	
	public Integer getPatientCountByMemberIdAndOtherCriteria(String clientName, String memberId, String firstName,
													         String lastName, Date dateOfBirth);
	
	public Integer getPatientCountByAltMemberIdAndOtherCriteria(String clientName, String altMemberId, String firstName,
		    										            String lastName, Date dateOfBirth);	
}
