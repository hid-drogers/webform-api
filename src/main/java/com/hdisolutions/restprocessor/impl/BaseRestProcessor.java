package com.hdisolutions.restprocessor.impl;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdisolutions.model.dto.BaseInvokerRequest;
import com.hdisolutions.model.dto.BaseInvokerResponse;
import com.hdisolutions.restprocessor.RestProcessor;
import com.hdisolutions.web.util.HDIRestConstants;

/**
 * This is the parent class that all rest processor's should extend. It contains default behavior and abstract methods,
 * called from the generic "flow" in HDIRestController.process(): validateRequest(), processRequest(). 
 * 
 * @author darrin.rogers
 *
 */
public abstract class BaseRestProcessor implements RestProcessor {

	@Value( "${msg.err.unexpected.error}" )
	private String msgUnexpectedError;	
	
	@Value( "$msg.err.contact.tech.support}" )
	private String msgContactTechSupport;		
	
	/**
	 * Subclasses should return an HttpStatus of 400/Bad Request in ResponseEntity, if validation fails. Controller will only
	 * call processRequest() if this method returns HttpStatus of 200/Ok in ResponseEntity.
	 */
	public ResponseEntity<BaseInvokerResponse> validateRequest(HttpServletRequest request, ResponseEntity<BaseInvokerResponse> authResponseEntity){
		// By default just return a ResponseEntity matching that passed from auth result (i.e., no validation performed)
		return new ResponseEntity<BaseInvokerResponse>(authResponseEntity.getBody(), authResponseEntity.getStatusCode());
	}	
	
	@Override
	public abstract ResponseEntity<BaseInvokerResponse> processRequest(ContentCachingRequestWrapper requestWrapper, ResponseEntity<BaseInvokerResponse> responseEntityFromAuth);
	
	/**
	 * Subclass should implement and return an implementation of BaseInvokerResponse
	 */
	protected abstract BaseInvokerResponse makeResponseEntityObject();

	/**
	 * Returns the specific implementation class for the request object.
	 */
	@SuppressWarnings("rawtypes")
	protected abstract Class getRequestEntityClass();	
	
	/**
	 * Uses ObjectMapper to make an object of the specific implementation class passed in, 
	 * using the HTTP body as string. This is specific to POST and PUT requests.
	 */
	protected BaseInvokerRequest getRequestModel(ContentCachingRequestWrapper requestWrapper, Class<BaseInvokerRequest> classType) {
		
		String requestBodyAsString = null;
		BaseInvokerRequest requestObj = null;
		try {
			requestBodyAsString = (String)getRequestModel(requestWrapper);
			ObjectMapper objMapper = new ObjectMapper();
			requestObj = (BaseInvokerRequest)objMapper.readValue(requestBodyAsString, classType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return requestObj;
	}	
	
	protected ResponseEntity<BaseInvokerResponse> makeServerErrorResponse(Exception e){
		
		BaseInvokerResponse responseObj = new BaseInvokerResponse();
		responseObj.setErrorOccurred(true);
		responseObj.setErrorMessage(msgUnexpectedError);
		responseObj.setResponseText(msgContactTechSupport + ": " + ExceptionUtils.getStackTrace(e));
		
		return new ResponseEntity<BaseInvokerResponse>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	protected String getClient(HttpServletRequest request) {
		return request.getHeader(HDIRestConstants.CLIENT);
	}

	protected String getInvoker(HttpServletRequest request) {
		return request.getHeader(HDIRestConstants.INVOKER);
	}	
	
	protected void setValidationError(BaseInvokerResponse responseModel, String errMsg) {
	
		responseModel.setErrorOccurred(true);
		responseModel.setErrorMessage(errMsg);
	}
	
	private Object getRequestModel(ContentCachingRequestWrapper requestWrapper) throws IOException {
		
		String requestBodyAsString = null;
		if("POST".equalsIgnoreCase(requestWrapper.getMethod())) {
			requestBodyAsString = new String(requestWrapper.getContentAsByteArray());
			if(StringUtils.isBlank(requestBodyAsString)) {
				requestBodyAsString = new String(IOUtils.toString(requestWrapper.getInputStream(), Charset.defaultCharset()));
			}
		}
		
		return requestBodyAsString;
	}	
}
