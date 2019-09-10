package com.hdisolutions.oltpservices.restprocessor.impl.physician;

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

import com.hdisolutions.model.domain.oltp.Physician;
import com.hdisolutions.model.dto.BaseInvokerRequest;
import com.hdisolutions.model.dto.BaseInvokerResponse;
import com.hdisolutions.oltpservices.model.dto.physician.GetPhysiciansResponse;
import com.hdisolutions.oltpservices.model.dto.physician.PhysicianCriteriaModel;
import com.hdisolutions.oltpservices.model.dto.physician.PhysicianDTO;
import com.hdisolutions.restprocessor.impl.BaseRestProcessor;
import com.hdisolutions.service.oltp.PhysicianService;

/**
 * Default behavior verifies a non-empty "NPI" request attribute. If valid, a List of Physician objects
 * matching passed NPI is retrieved and set in response entity model.
 * 
 * @author darrin.rogers
 *
 */
@Component
public class GetPhysiciansProcessor extends BaseRestProcessor {

	private static final Log log = LogFactory.getLog(GetPhysiciansProcessor.class);

	@Value( "${msg.err.validation.npi.cannot.be.empty}" )
	private String msgNpiCannotBeEmpty;	
		
	@Inject
	private PhysicianService physicianService;
	
	@SuppressWarnings("unchecked")
	@Override
	/** 
	 *  Override in client specific subclass if NPI is different attribute, or it uses other logic for physician validation.
	 *  If client/invoker override but they also use NPI, call reusable validateNPI() in overridden method. */
	public ResponseEntity<BaseInvokerResponse> validateRequest(ContentCachingRequestWrapper requestWrapper, 
															   ResponseEntity<BaseInvokerResponse> authResponseEntity) {
		
		
		String npi = ((PhysicianCriteriaModel)getRequestModel(requestWrapper, getRequestEntityClass())).getNpi();
		BaseInvokerResponse responseModel = new BaseInvokerResponse();
		HttpStatus returnStatus = validateNPI(npi, responseModel);
		
		return new ResponseEntity<BaseInvokerResponse>(responseModel, returnStatus);
	}

	@Override
	/** Override in client/invoker subclass if logic is different. Or perhaps just override getResponseWithPhysicians()
	 *  if getting by something other than NPI */
	public ResponseEntity<BaseInvokerResponse> processRequest(ContentCachingRequestWrapper requestWrapper,
															  ResponseEntity<BaseInvokerResponse> responseEntityFromAuth) {
		
		ResponseEntity<BaseInvokerResponse> responseEntity = null;
		
		String npi = requestWrapper.getParameter("npi");
		responseEntity = getResponseWithPhysicians(getClient(requestWrapper), npi);
		log.info("Get physicians. Response code: " + responseEntity.getStatusCodeValue());
		
		return responseEntity;
	}

	@SuppressWarnings("unchecked")
	protected ResponseEntity<BaseInvokerResponse> getResponseWithPhysicians(String client, String npi){
		
		GetPhysiciansResponse getPhysiciansResponse = (GetPhysiciansResponse)makeResponseEntityObject();
		List<Physician> physicianList = physicianService.getPhysicians(client, npi);
		List<PhysicianDTO> physicianDTOList = CollectionUtils.transform(physicianList, new PhysicianListTransformer());
		getPhysiciansResponse.setPhysicians(physicianDTOList);
		
		log.info("Physicians found for passed npi: " + (physicianDTOList == null?0:physicianList.size()));
		
		ResponseEntity<BaseInvokerResponse> responseEntity = new ResponseEntity<BaseInvokerResponse>(getPhysiciansResponse, HttpStatus.OK);
		
		return responseEntity;
	}	
	
	protected HttpStatus validateNPI(String npi, BaseInvokerResponse responseModel) {

		HttpStatus returnStatus = HttpStatus.OK;
		if(StringUtils.isBlank(npi)) {
			returnStatus = HttpStatus.BAD_REQUEST;
			responseModel.setErrorOccurred(true);
			responseModel.setErrorMessage(msgNpiCannotBeEmpty);
		}
		
		return returnStatus;
	}	
	
	@Override
	protected BaseInvokerRequest getRequestModel(ContentCachingRequestWrapper requestWrapper, Class<BaseInvokerRequest> classType) {
		
		PhysicianCriteriaModel requestModel = new PhysicianCriteriaModel();
		requestModel.setNpi(requestWrapper.getParameter("npi"));
		
		return requestModel;
	}
	
	@Override
	protected BaseInvokerResponse makeResponseEntityObject() {
		return new GetPhysiciansResponse();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getRequestEntityClass() {
		return PhysicianCriteriaModel.class;
	}

	class PhysicianListTransformer implements Transformer {

		@Override
		public Object transform(Object physician) {
			
			PhysicianDTO physicianDTO = new PhysicianDTO();
			BeanUtils.copyProperties(physician, physicianDTO);
			
			return physicianDTO;
		}
	}
	
}
