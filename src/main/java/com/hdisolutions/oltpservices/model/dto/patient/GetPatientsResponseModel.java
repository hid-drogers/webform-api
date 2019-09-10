package com.hdisolutions.oltpservices.model.dto.patient;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hdisolutions.model.dto.BaseInvokerResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonPropertyOrder({ "patients", "errorOccurred", "errorMessage", "errorSeverity", "responseText", "statusCode" })
public class GetPatientsResponseModel extends BaseInvokerResponse {
	
	private List<PatientDTO> patients;
}
