package com.migration.migration.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;

import com.migration.migration.entity.KycData;
import com.migration.migration.proxy.MigrationClient;

@Component
public class MigrationProcessHelper {
	

	@Autowired
	private MigrationClient migrationClient;
	
	@Async
	@Transactional
	public void migrate(KycData kycData) {
		
		migrationClient.saveUserKyc(kycData);
	}
	
	

}
