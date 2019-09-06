package com.hdisolutions.repository.oltp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hdisolutions.model.domain.oltp.Drug;

@Repository
public interface DrugRepository extends CrudRepository<Drug, Integer> {
	
	List<Drug> findByFullNameStartingWithIgnoreCase(@Param("name") String name);
}
