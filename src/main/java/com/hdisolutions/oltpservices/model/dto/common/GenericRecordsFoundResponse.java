package com.hdisolutions.oltpservices.model.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hdisolutions.model.dto.BaseInvokerResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonPropertyOrder({ "recordsFound", "ErrorOccurred", "ErrorMessage", "ErrorSeverity", 
			         "ResponseText", "StatusCode" })
public class GenericRecordsFoundResponse extends BaseInvokerResponse {
	
	@JsonProperty("recordsFound")
	private int recordsFound;
}
