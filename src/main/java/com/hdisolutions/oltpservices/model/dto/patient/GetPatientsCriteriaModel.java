package com.hdisolutions.oltpservices.model.dto.patient;

import com.hdisolutions.model.dto.BaseInvokerRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetPatientsCriteriaModel extends BaseInvokerRequest {
	
	private String firstName;
	private String lastName;
	private String memberId;
	private String dateOfBirth;
}
