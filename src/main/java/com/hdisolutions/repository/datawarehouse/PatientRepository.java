package com.hdisolutions.repository.datawarehouse;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hdisolutions.model.domain.datawarehouse.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {
	
	@Query("SELECT count(p) FROM Patient p WHERE p.memberId like :memberId% " +
			"AND UPPER(p.firstName) like :firstName% " +
			"AND UPPER(p.lastName) like :lastName% " +
			"AND p.dateOfBirth = :dateOfBirth")
	Integer getCountByMemberIdAndOtherCriteria(@Param("memberId") String memberId,
											   @Param("firstName") String firstName,
											   @Param("lastName") String lastName,
											   @Param("dateOfBirth") Date dateOfBirth);
	
	@Query("SELECT count(p) FROM Patient p WHERE p.altMemberId like :memberId% " +
			"AND UPPER(p.firstName) like :firstName% " +
			"AND UPPER(p.lastName) like :lastName% " +
			"AND p.dateOfBirth = :dateOfBirth")
	Integer getCountByAltMemberIdAndOtherCriteria(@Param("memberId") String memberId,
												  @Param("firstName") String firstName,
												  @Param("lastName") String lastName,
												  @Param("dateOfBirth") Date dateOfBirth);	
}
