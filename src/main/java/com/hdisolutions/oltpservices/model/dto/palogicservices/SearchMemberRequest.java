package com.hdisolutions.oltpservices.model.dto.palogicservices;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SearchMemberRequest {
	
	@JsonProperty("MemberNumber")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String memberNumber; // In HDI this is our memberId
	
	@JsonProperty("FirstName")
	private String firstName;
	
	@JsonProperty("LastName")
	private String lastName;
	
	@JsonProperty("Dob")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String dob;	
	
	@JsonProperty("PageNumber")
	private int pageNumber;
	
	@JsonProperty("PageSize")
	private int pageSize;		
	
}
