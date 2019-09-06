package com.hdisolutions.security.authenticator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.hdisolutions.model.dto.BaseInvokerResponse;

public interface RestRequestAuthenticator {
	
	public ResponseEntity<BaseInvokerResponse> authenticate(HttpServletRequest request);
	public String getClient(HttpServletRequest request);
	public String getInternalInvoker(HttpServletRequest request);
}
