package com.migration.migration.process;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.migration.migration.proxy.MigrationClient;
import com.migration.migration.request.PhrRequestPlayLoad;

@Component
public class MigrationProcessHelper {
	

	@Autowired
	private MigrationClient migrationClient;
	
	
	@Transactional
	public void migrate(PhrRequestPlayLoad phrRequestPlayLoad) {
		
		migrationClient.saveUserKyc(phrRequestPlayLoad);
	}
	
	

}
