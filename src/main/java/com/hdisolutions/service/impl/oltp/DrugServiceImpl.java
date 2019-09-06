package com.hdisolutions.service.impl.oltp;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hdisolutions.dao.oltp.DrugDAO;
import com.hdisolutions.model.domain.oltp.Drug;
import com.hdisolutions.service.oltp.DrugService;

@Service
public class DrugServiceImpl implements DrugService {

	@Inject
	private DrugDAO drugDAO;

	@Override
	public List<Drug> findByFullNameStartingWith(String clientName, String name) {
		return drugDAO.findByFullNameStartingWith(clientName, name);
	} 
}
