package com.hdisolutions.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.plexus.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.hdisolutions.model.dto.BaseInvokerResponse;
import com.hdisolutions.restprocessor.RestProcessor;
import com.hdisolutions.restprocessor.factory.RestProcessorFactory;
import com.hdisolutions.security.authenticator.RestRequestAuthenticator;
import com.hdisolutions.security.factory.RestReqAuthenticatorFactory;

/**
 * Base/parent controller class for HDI Rest services that support client specific: authentication,
 * request processing, and/or object type. Subclasses just need to call process() method, which will
 * use class and method name to make an authenticator and processor, which can both be client
 * specific. General "flow" is: authenticate, validate, process.
 * 
 * @author darrin.rogers
 *
 */
public class HDIRestController {
	
	private static final Log log = LogFactory.getLog(HDIRestController.class);
	
	@Value( "${msg.err.unexpected.error}" )
	private String msgUnexpectedError;		
	
	@Inject
	private RestReqAuthenticatorFactory authenticatorFactory;	
	
	@Inject
	private RestProcessorFactory processorFactory;	
	
	/**
	 * This method executes a generic/common "flow" for all service methods, with components designed
	 * to allow client specific processing/overrides. Default authenticator simply ensures a valid
	 * "client" header value is passed, or a 401 response code is set. If "invoker" is passed, it is
	 * validated against a RestServiceInternalInvoker enum. If authentication passes, the request
	 * is validated and if validation passes, processRequest() is called.
	 * 
	 * @param request
	 * @param response
	 * @param controllerClassSimpleName: Used for lookup of processor
	 * @param controllerMethodName: Used for lookup of processor
	 * @see RestServiceInternalInvoker
	 * @return
	 */
	protected ResponseEntity<BaseInvokerResponse> process(HttpServletRequest request, HttpServletResponse response, 
										    		      String controllerClassSimpleName, String controllerMethodName) {
		
		log.info("\n\nRequest received, authenticating invoker/client");
		ResponseEntity<BaseInvokerResponse> responseEntity = null;
		
		try {
			RestRequestAuthenticator authenticator = authenticatorFactory.makeRequestAuthenticator(request);
	    	ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
	    	
	    	// Authenticate the request, and proceed to validate request if 200/Ok auth response
	    	responseEntity = authenticator.authenticate(request);
	    	if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
	    		log.info("Invoker/client authenticated. Making processor for controller '" + controllerClassSimpleName +
	    				 "', method '" + controllerMethodName + "'");
	    		RestProcessor processor = processorFactory.makeProcessor(controllerClassSimpleName, controllerMethodName, authenticator.getClient(request));
	    		log.info("RestProcessor created. Implementation: " + processor.getClass());
	    		
	    		// Validate the request, and process request if validation returns a 200/Ok
	    		log.info("Validating the request (i.e., body for POST, params for GET)");
	    		responseEntity = processor.validateRequest(requestWrapper, responseEntity);
	    		if(HttpStatus.OK == responseEntity.getStatusCode()) {
	    			log.info("Request passed validation. Calling RestProcessor.processRequest().");
	    			responseEntity = processor.processRequest(requestWrapper, responseEntity);
	    			log.info("ResponseEntity returned from RestProcessor.processRequest(). Response code: " + responseEntity.getStatusCode());
	    		}else {
	    			log.info("Request validation failed. Response code: " + responseEntity.getStatusCode() + 
	    					 ", errorMessage: " + responseEntity.getBody().getErrorMessage()  + "\n");
	    		}
	    	}else {
	    		log.info("Request failed authentication. Response code: " + responseEntity.getStatusCode() + ", response body: " + responseEntity.getBody() + "\n");
	    	}			
		}catch(Exception e) {
			BaseInvokerResponse errResponse =  makeUnexpectedErrorResponse(e);
			responseEntity = new ResponseEntity<BaseInvokerResponse>(errResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
        return responseEntity;		
	};
	
	private BaseInvokerResponse makeUnexpectedErrorResponse(Exception e) {
		
		BaseInvokerResponse errResponse = new BaseInvokerResponse();
		errResponse.setErrorOccurred(true);
		errResponse.setResponseText(ExceptionUtils.getStackTrace(e));
		errResponse.setErrorMessage(msgUnexpectedError);
		
		return errResponse;
	}
}
