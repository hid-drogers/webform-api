package com.hdisolutions.oltpservices.restprocessor.impl.patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.cglib.core.Transformer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.hdisolutions.model.domain.datawarehouse.Patient;
import com.hdisolutions.model.dto.BaseInvokerRequest;
import com.hdisolutions.model.dto.BaseInvokerResponse;
import com.hdisolutions.oltpservices.model.dto.patient.GetPatientsCriteriaModel;
import com.hdisolutions.oltpservices.model.dto.patient.GetPatientsResponseModel;
import com.hdisolutions.oltpservices.model.dto.patient.PatientDTO;
import com.hdisolutions.restprocessor.impl.BaseRestProcessor;
import com.hdisolutions.service.datawarehouse.PatientService;

@Component
public class GetPatientsProcessor extends BaseRestProcessor {

	private static final Log log = LogFactory.getLog(GetPatientsProcessor.class);

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
		
		GetPatientsCriteriaModel getPatientsCriteriaModel = (GetPatientsCriteriaModel)getRequestModel(requestWrapper, getRequestEntityClass());
		BaseInvokerResponse responseModel = new BaseInvokerResponse();
		
		HttpStatus returnStatus = validatePatientSearchAttributes(getPatientsCriteriaModel, responseModel);
		
		return new ResponseEntity<BaseInvokerResponse>(responseModel, returnStatus);		
	}
	
	protected HttpStatus validatePatientSearchAttributes(GetPatientsCriteriaModel paramsModel, BaseInvokerResponse responseModel) {
		
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
		BaseInvokerRequest requestModel = getRequestModel(requestWrapper, getRequestEntityClass());
		ResponseEntity<BaseInvokerResponse> responseEntity = null;
		
		if(responseEntity == null) {
			responseEntity = getResponseWithPatients(getClient(requestWrapper), requestModel, requestWrapper);			
		}
		
		return responseEntity;
	}

	protected ResponseEntity<BaseInvokerResponse> getResponseWithPatients(String client, BaseInvokerRequest patientCriteriaModel,
																		  ContentCachingRequestWrapper requestWrapper) {
		
		GetPatientsResponseModel getPatientsResponseModel = (GetPatientsResponseModel)makeResponseEntityObject();
		GetPatientsCriteriaModel requestObj = (GetPatientsCriteriaModel)patientCriteriaModel;
		
		List<PatientDTO> patientDTOList = getPatientList(requestObj, requestWrapper);
		getPatientsResponseModel.setPatients(patientDTOList);
		log.info("Patient record count: " + (patientDTOList == null?0:patientDTOList.size()));
		
		return new ResponseEntity<BaseInvokerResponse>(getPatientsResponseModel, HttpStatus.OK);
	}
	
	// Some client extensions of this class override this method
	@SuppressWarnings("unchecked")
	protected List<PatientDTO> getPatientList(GetPatientsCriteriaModel requestObj, ContentCachingRequestWrapper requestWrapper){
		
		Date dateOfBirth = getDateOfBirthDateFromString(requestObj.getDateOfBirth());
		
		List<Patient> patientList = getPatientsFromService(requestObj, requestWrapper, dateOfBirth);
		List<PatientDTO> patientDTOList = CollectionUtils.transform(patientList, new PatientListTransformer());
		
		return patientDTOList;
	}
	
	protected List<Patient> getPatientsFromService(GetPatientsCriteriaModel requestObj, ContentCachingRequestWrapper requestWrapper,
												   Date dateOfBirth) {
		return patientDWService.getPatientsByMemberIdAndOtherCriteria(getClient(requestWrapper), requestObj.getMemberId(), 
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
		return new GetPatientsResponseModel();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getRequestEntityClass() {
		return GetPatientsCriteriaModel.class;
	}
}

class PatientListTransformer implements Transformer {

	@Override
	public Object transform(Object patient) {
		
		PatientDTO patientDTO = new PatientDTO();
		BeanUtils.copyProperties(patient, patientDTO);
		
		return patientDTO;
	}
}
