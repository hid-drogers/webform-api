package com.hdisolutions.oltpservices.model.dto.physician;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hdisolutions.model.dto.BaseInvokerResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonPropertyOrder({ "physicians", "errorOccurred", "errorMessage", "errorSeverity", "responseText", "statusCode" })
public class GetPhysiciansResponse extends BaseInvokerResponse {
	
	private List<PhysicianDTO> physicians;
}
