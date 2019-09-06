package com.hdisolutions.dao.oltp;

import java.util.List;

import com.hdisolutions.model.domain.oltp.Drug;

public interface DrugDAO {

	List<Drug> findByFullNameStartingWith(String clientName, String name);
}
