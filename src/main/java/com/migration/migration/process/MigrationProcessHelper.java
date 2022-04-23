package com.migration.migration.process;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.migration.migration.entity.UserEntity;
import com.migration.migration.proxy.CMMigrationClient;
import com.migration.migration.proxy.MigrationClient;
import com.migration.migration.request.PhrRequestPlayLoad;
import com.migration.migration.request.PhrUpdatePhotoProfileRequest;
import com.migration.migration.request.ShareCMRequestPlayLoad;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MigrationProcessHelper {

	@Autowired
	private MigrationClient migrationClient;

	@Autowired
	private CMMigrationClient cMMigrationClient;
    

	@Async("IO_Thread_Executor")
	public CompletableFuture<String> migrate(PhrRequestPlayLoad phrRequestPlayLoad) throws InterruptedException {
		if (log.isDebugEnabled())
		{
		log.debug("migrate to phr {} and thread name {}", phrRequestPlayLoad,Thread.currentThread().getName());
		}
		String success = "Y";
		try {
			migrationClient.phrMigration(phrRequestPlayLoad);
			log.debug("****** migrated is done for PHR {} ********", phrRequestPlayLoad.getAbhaAddress());

		} catch (Exception e) {
			success = "N";
			
			log.error("#######Exception occured While migrated to PHR AbhaAddress {} AbhaNumber {}#######", phrRequestPlayLoad.getAbhaAddress(),phrRequestPlayLoad.getAbhaNumber());
			 log.info("Payload {}",phrRequestPlayLoad.toString());
			log.info("error trace ", e);
		}
		return CompletableFuture.completedFuture(success);
   }
	
	@Async("IO_Thread_Executor")
	public CompletableFuture<Integer> migrate(PhrUpdatePhotoProfileRequest phrRequestPlayLoad) throws InterruptedException {
		
		Integer success = 1;
		try {
			boolean status = migrationClient.phrPhotoMigration(phrRequestPlayLoad);
			log.info("****** migrated is done for PHR {} and HealthId {}******** {}",phrRequestPlayLoad.getPhrAddress(), phrRequestPlayLoad.getHealthIdNumber(),status);
			success = status?1:0;		
		} catch (Exception e) {
			success = 0;
			
			log.error("#######Exception occured While migrated to PHR AbhaAddress {} AbhaNumber {}#######", phrRequestPlayLoad.getPhrAddress(),phrRequestPlayLoad.getHealthIdNumber());
			 log.info("Payload {}",phrRequestPlayLoad.toString());
			log.info("error trace ", e);
		}
		return CompletableFuture.completedFuture(success);
	}


	@Async("IO_Thread_Executor")
	public CompletableFuture<String> migrate(ShareCMRequestPlayLoad shareCMRequestPlayLoad) throws InterruptedException {
		if (log.isDebugEnabled())
		{
		log.debug("migrate to cm {} thread name {} ", shareCMRequestPlayLoad,Thread.currentThread().getName());
		}
		String success = "Y";
		try {

			cMMigrationClient.shareCMProfile(shareCMRequestPlayLoad);
			log.info("***migrated is done for CM {} *******", shareCMRequestPlayLoad.getPhrAddress());

		} catch (Exception e) {
			success = "N";
			log.error("#######Exception occured While migrated to CM AbhaAddress {} AbhaNumber {}####### ",shareCMRequestPlayLoad.getPhrAddress(),shareCMRequestPlayLoad.getHealthIdNumber());
		    log.info("Payload {}",shareCMRequestPlayLoad.toString());
			log.info("error trace ", e);
			
		}
		
		return CompletableFuture.completedFuture(success);
	}

	

}
