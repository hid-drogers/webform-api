package com.hdisolutions.oltpservices.model.dto.physician;

import com.hdisolutions.model.dto.BaseInvokerRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PhysicianCriteriaModel extends BaseInvokerRequest {
	
	private String npi;
}
