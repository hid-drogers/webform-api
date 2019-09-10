package com.hdisolutions.oltpservices.restprocessor.impl.drug;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.cglib.core.Transformer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.hdisolutions.model.domain.oltp.Drug;
import com.hdisolutions.model.dto.BaseInvokerRequest;
import com.hdisolutions.model.dto.BaseInvokerResponse;
import com.hdisolutions.oltpservices.model.dto.drug.DrugCriteriaModel;
import com.hdisolutions.oltpservices.model.dto.drug.DrugDTO;
import com.hdisolutions.oltpservices.model.dto.drug.GetDrugsResponse;
import com.hdisolutions.restprocessor.impl.BaseRestProcessor;
import com.hdisolutions.service.oltp.DrugService;

@Component
public class GetDrugsProcessor extends BaseRestProcessor {

	private static final Log log = LogFactory.getLog(GetDrugsProcessor.class);
	
	@Value( "${msg.err.validation.name.cannot.be.empty}" )
	private String msgNameCannotBeEmpty;
	
	@Inject
	private DrugService drugService;
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<BaseInvokerResponse> validateRequest(ContentCachingRequestWrapper requestWrapper,
															   ResponseEntity<BaseInvokerResponse> authResponseEntity) {

		DrugCriteriaModel requestModel = (DrugCriteriaModel)getRequestModel(requestWrapper, getRequestEntityClass());
		BaseInvokerResponse responseModel = new BaseInvokerResponse();
		HttpStatus returnStatus = validateName(requestModel.getName(), responseModel);
		
		return new ResponseEntity<BaseInvokerResponse>(responseModel, returnStatus);		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<BaseInvokerResponse> processRequest(ContentCachingRequestWrapper requestWrapper,
														      ResponseEntity<BaseInvokerResponse> responseEntityFromAuth) {
		
		BaseInvokerRequest requestModel = getRequestModel(requestWrapper, getRequestEntityClass());
		
		return getResponseWithDrugExistsBoolean(getClient(requestWrapper), requestModel);
	}
	
	/**
	 * Subclass class & override this method if they use a different model/attribute
	 */
	@SuppressWarnings("unchecked")
	protected ResponseEntity<BaseInvokerResponse> getResponseWithDrugExistsBoolean(String client, BaseInvokerRequest requestObj){
		
		DrugCriteriaModel getDrugCountCriteriaModel = (DrugCriteriaModel) requestObj;
		GetDrugsResponse recordCountResponse = (GetDrugsResponse)makeResponseEntityObject();
		
		List<Drug> drugList = drugService.findByFullNameStartingWith(client, getDrugCountCriteriaModel.getName());
		List<DrugDTO> drugDTOList = new ArrayList<DrugDTO>();
		drugDTOList = CollectionUtils.transform(drugList, new DrugListTransformer());
		
		recordCountResponse.setDrugs(drugDTOList);
		log.info("Drug record count: " + (drugList == null?0:drugList.size()));
		
		return new ResponseEntity<BaseInvokerResponse>(recordCountResponse, HttpStatus.OK);
	}	
	
	@Override
	protected BaseInvokerRequest getRequestModel(ContentCachingRequestWrapper requestWrapper, Class<BaseInvokerRequest> classType) {
		return new DrugCriteriaModel(requestWrapper.getParameter("name"));
	}	
	
	@Override
	protected BaseInvokerResponse makeResponseEntityObject() {
		return new GetDrugsResponse();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getRequestEntityClass() {
		return DrugCriteriaModel.class;
	}		
	
	private HttpStatus validateName(String name, BaseInvokerResponse responseModel) {
		
		HttpStatus returnStatus = (StringUtils.isEmpty((name))?HttpStatus.BAD_REQUEST:HttpStatus.OK);
		if(HttpStatus.BAD_REQUEST == returnStatus) {
			setValidationError(responseModel, msgNameCannotBeEmpty);
		}
		return returnStatus;
	}
}

class DrugListTransformer implements Transformer {

	@Override
	public Object transform(Object drug) {
		
		DrugDTO drugDTO = new DrugDTO();
		BeanUtils.copyProperties(drug, drugDTO);
		
		return drugDTO;
	}
}
