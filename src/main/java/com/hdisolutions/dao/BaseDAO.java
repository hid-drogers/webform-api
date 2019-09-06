package com.hdisolutions.dao;

import com.hdisolutions.multitenantdatasource.TenantContext;

public class BaseDAO {
	
	protected void setDataSourceLookupInfo(String dbType, String clientName) {
		TenantContext.setCurrentTenant(dbType + "_" + clientName.toLowerCase());
	}	
}
