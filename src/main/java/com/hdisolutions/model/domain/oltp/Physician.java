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
@Table(name = "physician")
public class Physician {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name="physician_id", insertable=false, updatable=false)
	private String physicianId;
	
	@Column(name="plan_id")
	private String planId;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="middle_name")
	private String middleName;
	
	@Column(name="date_of_birth")
	private Date dateOfBirth;
	
	@Column(name="email")
	private String email;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="changed_date")
	private Date changedDate;
	
	@Column(name="dea_number")
	private String deaNumber;
	
	@Column(name="cea_number")
	private String ceaNumber;
	
	@Column(name="suffix")
	private String suffix;
	
	@Column(name="prefix")
	private String prefix;
	
	@Column(name="effective_date")
	private Date effectiveDate;
	
	@Column(name="license_number")
	private String licenseNumber;
	
	@Column(name="par_non_status")
	private String parNonStatus;
	
	@Column(name="pcp_indicator")
	private String pcpIndicator;
	
	@Column(name="specialist_pcp")
	private String specialistPcp;
	
	@Column(name="term_Date")
	private Date termDate;
	
	@Column(name="physician_language")
	private String physicianLanguage;
	
	@Column(name="board_certification")
	private String boardCertification;
	
	@Column(name="maid_number")
	private String maidNumber;
	
	@Column(name="tax_id")
	private String taxId;
	
	@Column(name="delete_status")
	private String deleteStatus;
	
	@Column(name="npi")
	private String npi;
	
	@Column(name="provider_id")
	private String providerId;
	
	@Column(name="reconcile_flag")
	private String reconcileFlag;
	
	@Column(name="suffix_title")
	private String suffixTitle;
	
	@Column(name="specialty_code")
	private String specialtyCode;
	
	@Column(name="specialty_desc")
	private String specialtyDesc;
	
	@Column(name="specialty2_code")
	private String specialty2Code;
	
	@Column(name="specialty2_desc")
	private String specialty2Desc;
	
	@Column(name="specialty3_code")
	private String specialty3Code;
	
	@Column(name="specialty3_desc")
	private String specialty3Desc;
	
	@Column(name="uc_npi")
	private String ucNpi;
	
	@Column(name="lc_physician_id")
	private String lcPhysicianId;
	
	@Column(name="uc_last_name")
	private String ucLastName;
	
	@Column(name="uc_license_number")
	private String ucLicenseNumber;
	
	@Column(name="row_created_at")
	private Timestamp rowCreatedAt;
	
	@Column(name="row_modified_at")
	private Timestamp rowModifiedAt;	
}