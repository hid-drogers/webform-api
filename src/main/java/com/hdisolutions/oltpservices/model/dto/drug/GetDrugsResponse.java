package com.hdisolutions.oltpservices.model.dto.drug;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hdisolutions.model.dto.BaseInvokerResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonPropertyOrder({ "drugs", "errorOccurred", "errorMessage", "errorSeverity", "responseText", "statusCode" })
public class GetDrugsResponse extends BaseInvokerResponse {
	
	private List<DrugDTO> drugs;
}
