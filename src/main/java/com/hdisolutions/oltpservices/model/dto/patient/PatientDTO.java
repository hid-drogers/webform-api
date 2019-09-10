package com.hdisolutions.oltpservices.model.dto.patient;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class PatientDTO {

	private String clientClientId;	
	private String clientCustomerId;	
	private String clienGroupId;
	private Timestamp createDate;		
	private Timestamp lastModifiedTs;	
}