package com.hdisolutions.model.domain.oltp;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * TODO: Remove @Column annotations after figuring out why they are needed (i.e., spring.jpa.hibernate.naming-strategy
 * not working).
 */

@Data
@Entity
@Table(name = "drug_master")
public class Drug {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name="medid", insertable=false, updatable=false)
	private Integer medId;
	
	@Column(name="active_status")
	private String activeStatus;

	@Column(name="status_desc")
	private String statusDesc;	
	
	@Column(name="update_flag")
	private Integer updateFlag;		

	@Column(name="full_name")
	private String fullName;	
	
	@Column(name="route_name")
	private String routeName;		
	
	@Column(name="dosage_name")
	private String dosageName;			

	@Column(name="drug_name")
	private String drugName;	
	
	@Column(name="strength")
	private String strength;		

	@Column(name="unit_of_measure")
	private String unitOfMeasure;		
	
	@Column(name="gcn_seqno")
	private String gcnSeqno;		
	
	@Column(name="generic_med_id")
	private Integer genericMedId;		

	@Column(name="ndc")
	private String ndc;	

	@Column(name="ndc_add_date")
	private Date ndcAddDate;		

	@Column(name="ndc_update_date")
	private Date ndcUpdateDate;		
	
	@Column(name="ndc_obsolete_date")
	private String ndcObsoleteDate;	
	
	@Column(name="gcn")
	private String gcn;	
	
	@Column(name="hcpcs_code")
	private String hcpcsCode;		

	@Column(name="lc_full_name")
	private String lcFullName;			

	@Column(name="gpi")
	private String gpi;
	
	@Column(name="row_created_at")
	private Timestamp rowCreatedAt;
	
	@Column(name="row_modified_at")
	private Timestamp rowModifiedAt;	
}