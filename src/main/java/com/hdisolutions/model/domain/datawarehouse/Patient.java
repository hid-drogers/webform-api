package com.hdisolutions.model.domain.datawarehouse;

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
@Table(name = "patient")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name="pid", insertable=false, updatable=false)
	private Integer pid;
	
	@Column(name="member_id")
	private String memberId;
	
	@Column(name="address1")
	private String address1;	

	@Column(name="address2")
	private String address2;
	
	@Column(name="address3")
	private String address3;	

	@Column(name="alt2_member_id")
	private String alt2MemberId;		
	
	@Column(name="alt3_member_id")
	private String alt3MemberId;
	
	@Column(name="alt4_member_id")
	private String alt4MemberId;	
	
	@Column(name="alt_member_id")
	private String altMemberId;		

	@Column(name="auth_rep_on_file")
	private String authRepOnFile;		

	@Column(name="benefit_date")
	private Date benefitDate;		

	@Column(name="benefit_id")
	private String benefitId;		

	@Column(name="care_ind")
	private String careInd;		

	@Column(name="city")
	private String city;			

	@Column(name="client_client_id")
	private String clientClientId;	

	@Column(name="client_customer_id")
	private String clientCustomerId;	
	
	@Column(name="client_group_id")
	private String clienGroupId;	

	@Column(name="client_id")
	private String clientId;	

	@Column(name="county")
	private String county;	

	@Column(name="create_date")
	private Timestamp createDate;		

	@Column(name="customer_id")
	private String customerId;		

	@Column(name="date_of_birth")
	private Date dateOfBirth;		

	@Column(name="email")
	private String email;		
	
	@Column(name="fax")
	private String fax;	

	@Column(name="first_name")
	private String firstName;	

	@Column(name="gender")
	private String gender;	
	
	@Column(name="hearing_impaired")
	private String hearingImpaired;	

	@Column(name="hicn")
	private String hicn;	
	
	@Column(name="last_name")
	private String lastName;	
	
	@Column(name="medicaid_category_code")
	private String medicaidCategoryCode;	

	@Column(name="medicare_contract_number")
	private String medicareContractNumber;		

	@Column(name="middle_name")
	private String middleName;			
	
	@Column(name="patient_lang")
	private String patientLang;			

	@Column(name="pbp_date")
	private Date pbpDate;			
	
	@Column(name="pbp_id")
	private String pbpId;			

	@Column(name="pcp_on_file")
	private String pcpOnFile;	
	
	@Column(name="person_code")
	private String personCode;		
	
	@Column(name="phone")
	private String phone;		
	
	@Column(name="recipient_code")
	private String recipientCode;		
	
	@Column(name="rider_id")
	private String riderId;	
	
	@Column(name="ssn")
	private String ssn;		

	@Column(name="state")
	private String state;		

	@Column(name="tpl_payor")
	private String tplPayor;	
	
	@Column(name="tpl_prim_date")
	private Date tplPrimDate;	
	
	@Column(name="tpl_prim_ind")
	private String tplPrimInd;	

	@Column(name="tpl_prim_name")
	private String tplPrimName;	
	
	@Column(name="tpl_sec_date")
	private Date tplSecDate;	

	@Column(name="tpl_sec_name")
	private String tplSecName;		
	
	@Column(name="tpl_ter_date")
	private Date tplTerDate;		
	
	@Column(name="tpl_ter_name")
	private String tplTerName;		

	@Column(name="transplant_date")
	private Date transplantDate;		

	@Column(name="transplant")
	private String transplant;		
	
	@Column(name="update_date")
	private Date updateDate;	

	@Column(name="vision_impaired")
	private String visionImpaired;		
	
	@Column(name="zip")
	private String zip;		

	@Column(name="cms_contract_id")
	private String cmsContractId;		

	@Column(name="cms_plan_id")
	private String cmsPlanId;		

	@Column(name="last_modified_ts")
	private Timestamp lastModifiedTs;	

}