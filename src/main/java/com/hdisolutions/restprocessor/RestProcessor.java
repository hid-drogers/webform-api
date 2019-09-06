package com.hdisolutions.restprocessor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.hdisolutions.model.dto.BaseInvokerResponse;

public interface RestProcessor {

	public ResponseEntity<BaseInvokerResponse> validateRequest(ContentCachingRequestWrapper requestWrapper, ResponseEntity<BaseInvokerResponse> authResponseEntity);
	public ResponseEntity<BaseInvokerResponse> processRequest(ContentCachingRequestWrapper requestWrapper, ResponseEntity<BaseInvokerResponse> responseEntityFromAuth);
}
