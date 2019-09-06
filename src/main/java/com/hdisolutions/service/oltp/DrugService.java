package com.hdisolutions.service.oltp;

import java.util.List;

import com.hdisolutions.model.domain.oltp.Drug;

public interface DrugService {

	public List<Drug> findByFullNameStartingWith(String clientName, String name);
}
