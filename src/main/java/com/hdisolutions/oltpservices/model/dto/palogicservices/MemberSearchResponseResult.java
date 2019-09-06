package com.hdisolutions.oltpservices.model.dto.palogicservices;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MemberSearchResponseResult {
	
	@JsonProperty("List")
	private List<MemberSearchResponseMember> memberList;
	
	@JsonProperty("PageNumber")
	private int pageNumber;
	
	@JsonProperty("PageSize")
	private int pageSize;
	
	@JsonProperty("TotalItems")
	private int totalItems;
	
	@JsonProperty("TotalPages")
	private int totalPages;	
}