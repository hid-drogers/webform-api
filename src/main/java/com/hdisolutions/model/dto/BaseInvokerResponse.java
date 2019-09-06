package com.hdisolutions.model.dto;

import lombok.Data;

@Data
public class BaseInvokerResponse {

	private boolean errorOccurred;
	private String errorMessage;
	private String errorSeverity;
	private String responseText;
	private String statusCode;	
}
