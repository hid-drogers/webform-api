package com.hdisolutions.oltpservices.model.dto.patient;

import com.hdisolutions.model.dto.BaseInvokerRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetPatientCountCriteriaModel extends BaseInvokerRequest {
	
	private String firstName;
	private String lastName;
	private String memberId;
	private String dateOfBirth;
	
	public GetPatientCountCriteriaModel(String firstName, String lastName, String memberId, String dateOfBirth){
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.memberId = memberId;
		this.dateOfBirth = dateOfBirth;
	}
}
