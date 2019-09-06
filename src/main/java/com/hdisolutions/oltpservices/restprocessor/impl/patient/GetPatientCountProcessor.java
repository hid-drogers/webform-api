package com.hdisolutions.oltpservices.restprocessor.impl.patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.hdisolutions.model.dto.BaseInvokerRequest;
import com.hdisolutions.model.dto.BaseInvokerResponse;
import com.hdisolutions.oltpservices.model.dto.common.GenericRecordsFoundResponse;
import com.hdisolutions.oltpservices.model.dto.patient.GetPatientCountCriteriaModel;
import com.hdisolutions.restprocessor.impl.BaseRestProcessor;
import com.hdisolutions.service.datawarehouse.PatientService;

@Component
public class GetPatientCountProcessor extends BaseRestProcessor {

	private static final Log log = LogFactory.getLog(GetPatientCountProcessor.class);

	@Value( "${msg.err.validation.patient.request.params.cannot.be.empty}" )
	private String msgRequestParamsCannotBeEmpty;	
	
	@Value( "${msg.err.validation.date.of.birth.bad.format}" )
	private String msgBadDateFormat;		
	
	@Inject
	PatientService patientDWService;
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<BaseInvokerResponse> validateRequest(ContentCachingRequestWrapper requestWrapper,
															   ResponseEntity<BaseInvokerResponse> authResponseEntity) {
		
		GetPatientCountCriteriaModel getPatientCountParamsModel = (GetPatientCountCriteriaModel)getRequestModel(requestWrapper, getRequestEntityClass());
		BaseInvokerResponse responseModel = new BaseInvokerResponse();
		
		HttpStatus returnStatus = validatePatientSearchAttributes(getPatientCountParamsModel, responseModel);
		
		return new ResponseEntity<BaseInvokerResponse>(responseModel, returnStatus);		
	}
	
	@Override
	protected BaseInvokerRequest getRequestModel(ContentCachingRequestWrapper requestWrapper, Class<BaseInvokerRequest> classType) {
		
		String memberId = requestWrapper.getParameter("memberId");
		String firstName = requestWrapper.getParameter("firstName");
		String lastName = requestWrapper.getParameter("lastName");
		String dateOfBirth = requestWrapper.getParameter("dateOfBirth");
		
		return new GetPatientCountCriteriaModel(firstName, lastName, memberId, dateOfBirth);
	}
	
	protected HttpStatus validatePatientSearchAttributes(GetPatientCountCriteriaModel paramsModel, BaseInvokerResponse responseModel) {
		
		HttpStatus returnStatus = HttpStatus.OK;
		
		if(StringUtils.isBlank(paramsModel.getMemberId()) || StringUtils.isBlank(paramsModel.getFirstName()) ||
		  StringUtils.isBlank(paramsModel.getLastName()) || StringUtils.isBlank(paramsModel.getDateOfBirth())) {
			setValidationError(responseModel, msgRequestParamsCannotBeEmpty);
			returnStatus = HttpStatus.BAD_REQUEST;
		}else {
			try {
				getDateOfBirthDateFromString(paramsModel.getDateOfBirth());
			}catch(RuntimeException re) {
				// This means the date string could not be parsed to expected format
				setValidationError(responseModel, msgBadDateFormat);
				returnStatus = HttpStatus.BAD_REQUEST;				
			}
		}
		
		return returnStatus;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<BaseInvokerResponse> processRequest(ContentCachingRequestWrapper requestWrapper,
															  ResponseEntity<BaseInvokerResponse> responseEntityFromAuth) {
		
		// Get a list of patients matching ALL passed criteria. Set recordsFound attribute to size of List (or 0 if empty).
		BaseInvokerRequest requestModel = null;
		ResponseEntity<BaseInvokerResponse> responseEntity = null;
		
		try {
			requestModel = getRequestModel(requestWrapper, getRequestEntityClass());
		}catch(Exception e) {
			responseEntity = makeServerErrorResponse(e);
		}
		
		if(responseEntity == null) {
			responseEntity = getResponseWithPatientCount(getClient(requestWrapper), requestModel, requestWrapper);			
		}
		
		return responseEntity;
	}

	// Some client extensions of this class override this method
	protected ResponseEntity<BaseInvokerResponse> getResponseWithPatientCount(String client, BaseInvokerRequest patientCriteriaModel,
																			  ContentCachingRequestWrapper requestWrapper) {
		
		GenericRecordsFoundResponse getRecordCountResponse = (GenericRecordsFoundResponse)makeResponseEntityObject();
		GetPatientCountCriteriaModel requestObj = (GetPatientCountCriteriaModel)patientCriteriaModel;
		
		Integer recordCount = getPatientCount(requestObj, requestWrapper);
		getRecordCountResponse.setRecordsFound(recordCount);
		log.info("Patient record count: " + recordCount);
		
		return new ResponseEntity<BaseInvokerResponse>(getRecordCountResponse, HttpStatus.OK);
	}
	
	protected Integer getPatientCount(GetPatientCountCriteriaModel requestObj, ContentCachingRequestWrapper requestWrapper){
		
		Integer recordCount = null;
		
		Date dateOfBirth = getDateOfBirthDateFromString(requestObj.getDateOfBirth());
		recordCount = getPatientCountFromService(requestObj, requestWrapper, dateOfBirth);
		
		return recordCount;
	}
	
	protected Integer getPatientCountFromService(GetPatientCountCriteriaModel requestObj, ContentCachingRequestWrapper requestWrapper, Date dateOfBirth) {
		return patientDWService.getPatientCountByMemberIdAndOtherCriteria(getClient(requestWrapper), requestObj.getMemberId(), 
				 														  requestObj.getFirstName(), requestObj.getLastName(), dateOfBirth);		
	}
	
	protected Date getDateOfBirthDateFromString(String dateOfBirthString) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateOfBirth;
		try {
			dateOfBirth = sdf.parse(dateOfBirthString);
		} catch (ParseException e) {
			// This should never happen as validation logic checks for correct DOB format. Throw RuntimeException.
			throw new RuntimeException(e);
		}
		
		return dateOfBirth;
	}
	
	@Override
	protected BaseInvokerResponse makeResponseEntityObject() {
		return new GenericRecordsFoundResponse();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getRequestEntityClass() {
		return GetPatientCountCriteriaModel.class;
	}

}
