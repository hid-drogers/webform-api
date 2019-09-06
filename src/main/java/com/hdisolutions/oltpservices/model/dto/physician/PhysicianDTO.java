package com.hdisolutions.oltpservices.model.dto.physician;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@Data
public class PhysicianDTO {

	private String physicianId;
	private String planId;
	private String lastName;
	private String firstName;
	private String middleName;
	private Date dateOfBirth;
	private String email;
	private String gender;
	private Date creationDate;
	private Date changedDate;
	private String deaNumber;
	private String ceaNumber;
	private String suffix;
	private String prefix;
	private Date effectiveDate;
	private String licenseNumber;
	private String parNonStatus;
	private String pcpIndicator;
	private String specialistPcp;
	private Date termDate;
	private String physicianLanguage;
	private String boardCertification;
	private String maidNumber;
	private String taxId;
	private String deleteStatus;
	private String npi;
	private String providerId;
	private String reconcileFlag;
	private String suffixTitle;
	private String specialtyCode;
	private String specialtyDesc;
	private String specialty2Code;
	private String specialty2Desc;
	private String specialty3Code;
	private String specialty3Desc;
	private String ucNpi;
	private String lcPhysicianId;
	private String ucLastName;
	private String ucLicenseNumber;
	private Timestamp rowCreatedAt;
	private Timestamp rowModifiedAt;	
}