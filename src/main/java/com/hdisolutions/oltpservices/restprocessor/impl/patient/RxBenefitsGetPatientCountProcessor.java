package com.hdisolutions.oltpservices.restprocessor.impl.patient;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.hdisolutions.oltpservices.model.dto.patient.GetPatientCountCriteriaModel;

/**
 * This RxBenefits extension of the default processor differs in which service method is used to get the count
 * (i.e., RxBenefits uses altMemberId, where the default is memberId).
 * 
 * @author darrin.rogers
 *
 */

@Component
public class RxBenefitsGetPatientCountProcessor extends GetPatientCountProcessor {
	
	protected Integer getPatientCountFromService(GetPatientCountCriteriaModel requestObj, ContentCachingRequestWrapper requestWrapper, Date dateOfBirth) {
		return patientDWService.getPatientCountByAltMemberIdAndOtherCriteria(getClient(requestWrapper), requestObj.getMemberId(), 
  			    															 requestObj.getFirstName(), requestObj.getLastName(), dateOfBirth);			
	}
}
