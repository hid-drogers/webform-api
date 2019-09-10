package com.hdisolutions.oltpservices.restprocessor.impl.patient;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.hdisolutions.model.domain.datawarehouse.Patient;
import com.hdisolutions.oltpservices.model.dto.patient.GetPatientsCriteriaModel;

/**
 * This RxBenefits extension of the default processor differs in which service method is used to get the
 * List of Patient objects (i.e., RxBenefits uses altMemberId, where the default is memberId).
 * 
 * @author darrin.rogers
 *
 */

@Component
public class RxBenefitsGetPatientsProcessor extends GetPatientsProcessor {
	
	protected List<Patient> getPatientsFromService(GetPatientsCriteriaModel requestObj, ContentCachingRequestWrapper requestWrapper, Date dateOfBirth) {
		return patientDWService.getPatientsByAltMemberIdAndOtherCriteria(getClient(requestWrapper), requestObj.getMemberId(), 
  			    														 requestObj.getFirstName(), requestObj.getLastName(), dateOfBirth);			
	}
}
