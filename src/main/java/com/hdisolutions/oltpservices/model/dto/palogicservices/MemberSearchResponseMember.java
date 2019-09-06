package com.hdisolutions.oltpservices.model.dto.palogicservices;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MemberSearchResponseMember {

	@JsonProperty("MemberNumber")
	private String memberNumber; // In HDI this is our memberId
	
	@JsonProperty("MemberCardId")
	private String memberCardId; // Unique Patient ID (HDI Field: Alternate ID)
	
	@JsonProperty("FirstName")
	private String firstName;
	
	@JsonProperty("LastName")
	private String lastName;
	
	@JsonProperty("Dob")
	private String dob;	
	
	@JsonProperty("PersonCode")
	private String personCode;
	
	@JsonProperty("OrganizationName") // Currently we ignore this
	private String organizationName;
	
	@JsonProperty("CarrierName") // Currently we ignore this
	private String carrierName;
	
	@JsonProperty("AccountName") // Currently we ignore this
	private String accountName;
	
	@JsonProperty("GroupName") // Currently we ignore this
	private String groupName;
	
	@JsonProperty("OrganizationId")
	private String organizationId;

	@JsonProperty("OrganizationNumber")
	private String organizationNumber;	
	
	@JsonProperty("CarrierId")
	private String carrierId;

	@JsonProperty("CarrierNumber") 
	private String carrierNumber;		
	
	@JsonProperty("AccountId")
	private String accountId;	
	
	@JsonProperty("AccountNumber")
	private String accountNumber;	
	
	@JsonProperty("GroupId")
	private String groupId;		
	
	@JsonProperty("GroupNumber")
	private String groupNumber;		
	
	@JsonProperty("CreatedDate")
	private String createdDate;  
}
