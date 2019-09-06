package com.hdisolutions.dao.impl.oltp;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.hdisolutions.dao.BaseDAO;
import com.hdisolutions.dao.DAOConstants;
import com.hdisolutions.dao.oltp.DrugDAO;
import com.hdisolutions.model.domain.oltp.Drug;
import com.hdisolutions.repository.oltp.DrugRepository;

@Component
public class DrugDAOImpl extends BaseDAO implements DrugDAO {

	@Inject
	private DrugRepository drugRepo;
	
	@Override
	public List<Drug> findByFullNameStartingWith(String clientName, String name) {
		
		setDataSourceLookupInfo(clientName);
		
		return drugRepo.findByFullNameStartingWithIgnoreCase(name);
	}

	private void setDataSourceLookupInfo(String clientName) {
		setDataSourceLookupInfo(DAOConstants.DB_TYPE_OLTP, clientName);
	}
}
