package com.hdisolutions.oltpservices.model.dto.drug;

import com.hdisolutions.model.dto.BaseInvokerRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DrugCriteriaModel extends BaseInvokerRequest {
	
	private String name;	
	
	public DrugCriteriaModel(String name) {
		this.name = name;
	}
}
