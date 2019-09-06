package com.hdisolutions.security.authenticator.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.hdisolutions.web.util.HDIRestConstants;
import com.hdisolutions.enums.HDIClient;
import com.hdisolutions.enums.RestServiceInternalInvoker;
import com.hdisolutions.model.dto.BaseInvokerResponse;
import com.hdisolutions.security.authenticator.RestRequestAuthenticator;

@Component
public class BaseRequestAuthenticator implements RestRequestAuthenticator {

	@Value( "${msg.err.unauthorized.client.cannot.be.empty}" )
	private String msgClientCannotBeEmpty;		
	
	@Value( "${msg.err.unauthorized.client.invalid}" )
	private String msgInvalidClient;			
	
	@Value( "${msg.err.unauthorized.invoker.invalid}" )
	private String msgInvalidInvoker;	
	
	@Override
	public ResponseEntity<BaseInvokerResponse> authenticate(HttpServletRequest request) {

		BaseInvokerResponse responseModel = new BaseInvokerResponse();

		HttpStatus returnStatus = HttpStatus.OK;
		String clientName = getClient(request);
		if (clientName == null) {
			returnStatus = HttpStatus.UNAUTHORIZED;
			responseModel.setErrorOccurred(true);
			responseModel.setErrorMessage(msgClientCannotBeEmpty);
		} else {
			HDIClient hdiClient = HDIClient.forName(clientName);
			if (hdiClient == null) {
				returnStatus = HttpStatus.UNAUTHORIZED;
				responseModel.setErrorOccurred(true);
				responseModel.setErrorMessage(msgInvalidClient + ": " + clientName);
			} else {
				String intervalInvokerName = getInternalInvoker(request);
				if (intervalInvokerName != null) { // NOTE: It's ok to be null. If so, assume request is from a remote client.
					RestServiceInternalInvoker internalInvoker = RestServiceInternalInvoker.forName(intervalInvokerName);
					if (internalInvoker == null) {
						returnStatus = HttpStatus.UNAUTHORIZED;
						responseModel.setErrorOccurred(true);
						responseModel.setErrorMessage(msgInvalidInvoker + ": " + intervalInvokerName);
					}
				}
			}
		}

		return new ResponseEntity<BaseInvokerResponse>(responseModel, returnStatus);
	}

	public String getClient(HttpServletRequest request) {
		return request.getHeader(HDIRestConstants.CLIENT); // Default implementation gets from "client" header
	}

	public String getInternalInvoker(HttpServletRequest request) {
		return request.getHeader(HDIRestConstants.INVOKER); // Default implementation gets from "invoker" header
	}
}
