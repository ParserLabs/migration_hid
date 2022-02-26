package com.migration.migration.process;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.migration.migration.proxy.CMMigrationClient;
import com.migration.migration.proxy.MigrationClient;
import com.migration.migration.request.PhrRequestPlayLoad;
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
		} catch (Exception e) {
			success = "N";
			log.error("Exception occured While migrated to phr", e.getMessage());
		}
		if (log.isDebugEnabled()) {
		log.debug("migrated is done {} ", phrRequestPlayLoad.getAbhaAddress());
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
		} catch (Exception e) {
			success = "N";
			log.error("Exception occured While migrated to cm", e.getMessage());
		}
		if (log.isDebugEnabled())
		{
		log.debug("migrated is done {} ", shareCMRequestPlayLoad.getPhrAddress());
		}
		return CompletableFuture.completedFuture("N");
	}

}
