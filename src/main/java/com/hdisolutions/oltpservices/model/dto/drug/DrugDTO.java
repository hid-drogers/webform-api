package com.hdisolutions.oltpservices.model.dto.drug;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;

import lombok.Data;

/**
 * DTO object based on domain entity. Included in Drug related service responses.
 */

@Data
public class DrugDTO {

	private Integer medId;
	private String activeStatus;
	private String statusDesc;	
	private Integer updateFlag;		
	private String fullName;	
	private String routeName;		
	private String dosageName;			
	private String drugName;	
	private String strength;		
	private String unitOfMeasure;		
	private String gcnSeqno;		
	private Integer genericMedId;		
	private String ndc;	
	private Date ndcAddDate;		
	private Date ndcUpdateDate;		
	private String ndcObsoleteDate;	
	private String gcn;	
	private String hcpcsCode;		
	private String lcFullName;			
	private String gpi;
	private Timestamp rowCreatedAt;
	private Timestamp rowModifiedAt;		
}