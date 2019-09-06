package com.hdisolutions.oltpservices.restprocessor.impl.patient;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.hdisolutions.oltpservices.model.dto.palogicservices.MemberSearchResponse;
import com.hdisolutions.oltpservices.model.dto.palogicservices.SearchMemberRequest;
import com.hdisolutions.oltpservices.model.dto.patient.GetPatientCountCriteriaModel;

/**
 * This RxSense extension of the default processor differs in how it gets the count of patients, which is
 * by calling the Member Search rest service in palogicservices.
 * 
 * @author darrin.rogers
 *
 */

@Component
public class RxSenseGetPatientCountProcessor extends GetPatientCountProcessor {

	private static final Log log = LogFactory.getLog(RxSenseGetPatientCountProcessor.class);
	
	@Value("${ws.palogicservices.membersearch.url}")
    private String palogicservicesMemberSearchUrl;
	
	@Inject
	private RestTemplate palogicServicesRestTemplate;
	
	// Overriding default behavior in parent class. Count comes from remote service for RxSense, not a query
	protected Integer getPatientCount(GetPatientCountCriteriaModel requestObj, ContentCachingRequestWrapper requestWrapper) {

		SearchMemberRequest searchMemberRequest = convertRequestModelToSearchMemberRequest(requestObj);
		RequestEntity<SearchMemberRequest> requestEntity = new RequestEntity<SearchMemberRequest>(searchMemberRequest, null, null);		
		
		log.info("Calling Palogic Services Member Search service ...");
		ResponseEntity<MemberSearchResponse> responseEntity =
				palogicServicesRestTemplate.exchange(palogicservicesMemberSearchUrl, HttpMethod.POST, requestEntity, MemberSearchResponse.class);
		log.info("Palogic Services Member Search response code: " + responseEntity.getStatusCode());
		
		return getCountFromMemberSearchResponse(responseEntity);
	}
	
	private Integer getCountFromMemberSearchResponse(ResponseEntity<MemberSearchResponse> responseEntity) {
		
		int recordsFound = 0;
		if(responseEntity != null && responseEntity.getBody() != null) {
			MemberSearchResponse palogicServiceResponseObj = responseEntity.getBody();
			if(palogicServiceResponseObj.getResult() != null && palogicServiceResponseObj.getResult().getMemberList() != null) {
				recordsFound = palogicServiceResponseObj.getResult().getMemberList().size();
			}
		}
		
		return recordsFound;
	}
	
	private SearchMemberRequest convertRequestModelToSearchMemberRequest(GetPatientCountCriteriaModel requestObj) {
		
		SearchMemberRequest searchMemberRequest = new SearchMemberRequest();
		searchMemberRequest.setMemberNumber(requestObj.getMemberId());
		searchMemberRequest.setFirstName(requestObj.getFirstName());
		searchMemberRequest.setLastName(requestObj.getLastName());
		searchMemberRequest.setDob(requestObj.getDateOfBirth());
		
		return searchMemberRequest;
	}
}
