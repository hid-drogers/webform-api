package com.hdisolutions.oltpservices.restprocessor.impl.patient;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.cglib.core.Transformer;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.hdisolutions.oltpservices.model.dto.palogicservices.MemberSearchResponse;
import com.hdisolutions.oltpservices.model.dto.palogicservices.MemberSearchResponseMember;
import com.hdisolutions.oltpservices.model.dto.palogicservices.SearchMemberRequest;
import com.hdisolutions.oltpservices.model.dto.patient.GetPatientsCriteriaModel;
import com.hdisolutions.oltpservices.model.dto.patient.PatientDTO;

/**
 * This RxSense extension of the default processor differs in how it gets the count of patients, which is
 * by calling the Member Search rest service in palogicservices.
 * 
 * @author darrin.rogers
 *
 */

@Component
public class RxSenseGetPatientsProcessor extends GetPatientsProcessor {

	private static final Log log = LogFactory.getLog(RxSenseGetPatientsProcessor.class);
	
	@Value("${ws.palogicservices.membersearch.url}")
    private String palogicservicesMemberSearchUrl;
	
	@Inject
	private RestTemplate palogicServicesRestTemplate;
	
	// Overriding default behavior in parent class. Count comes from remote service for RxSense, not a query
	@SuppressWarnings("unchecked")
	protected List<PatientDTO> getPatientList(GetPatientsCriteriaModel requestObj, ContentCachingRequestWrapper requestWrapper) {

		SearchMemberRequest searchMemberRequest = convertRequestModelToSearchMemberRequest(requestObj);
		RequestEntity<SearchMemberRequest> requestEntity = new RequestEntity<SearchMemberRequest>(searchMemberRequest, null, null);		
		
		log.info("Calling Palogic Services Member Search service ...");
		ResponseEntity<MemberSearchResponse> responseEntity =
				palogicServicesRestTemplate.exchange(palogicservicesMemberSearchUrl, HttpMethod.POST, requestEntity, MemberSearchResponse.class);
		log.info("Palogic Services Member Search response code: " + responseEntity.getStatusCode());
		
		List<PatientDTO> patientDTOList = new ArrayList<PatientDTO>();
		if(responseEntity != null && responseEntity.getBody() != null && responseEntity.getBody().getResult() != null &&
		   responseEntity.getBody().getResult().getMemberList() != null && 
		   responseEntity.getBody().getResult().getMemberList().size() > 0){
			 patientDTOList = CollectionUtils.transform(responseEntity.getBody().getResult().getMemberList(), new MemberListTransformer());
		}
		
		return patientDTOList;
	}
	
	private SearchMemberRequest convertRequestModelToSearchMemberRequest(GetPatientsCriteriaModel requestObj) {
		
		SearchMemberRequest searchMemberRequest = new SearchMemberRequest();
		searchMemberRequest.setMemberNumber(requestObj.getMemberId());
		searchMemberRequest.setFirstName(requestObj.getFirstName());
		searchMemberRequest.setLastName(requestObj.getLastName());
		searchMemberRequest.setDob(requestObj.getDateOfBirth());
		
		return searchMemberRequest;
	}
}

class MemberListTransformer implements Transformer {

	private static final Log transformerLog = LogFactory.getLog(MemberListTransformer.class);
	
	@Override
	public Object transform(Object member) {
		
		MemberSearchResponseMember typedMember = (MemberSearchResponseMember)member;
		PatientDTO patientDTO = new PatientDTO();
		
		patientDTO.setClientCustomerId(typedMember.getCarrierNumber()); // Plan
		patientDTO.setClientClientId(typedMember.getAccountNumber()); // Lob
		patientDTO.setClienGroupId(typedMember.getGroupNumber()); // Group
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");  
		Date createdDate = null;
		try {
			createdDate = sdf.parse(typedMember.getCreatedDate());
		} catch (ParseException e) {
			transformerLog.error("Could not parse createdDate of MemberSearchResponseMember from rxsense", e);
		}
		
		if(createdDate != null) {
			Timestamp createTimestamp = new Timestamp(createdDate.getTime());
			patientDTO.setCreateDate(createTimestamp);
			patientDTO.setLastModifiedTs(createTimestamp);
		}
		
		return patientDTO;
	}
}

