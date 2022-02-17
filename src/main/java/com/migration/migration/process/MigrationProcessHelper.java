package com.migration.migration.process;

import org.springframework.beans.factory.annotation.Autowired;
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

	public String migrate(PhrRequestPlayLoad phrRequestPlayLoad) {
		log.info("migrate to phr {} ", phrRequestPlayLoad);
		String success = "Y";
		try {
			migrationClient.phrMigration(phrRequestPlayLoad);
		} catch (Exception e) {
			success = "N";
			log.error("Exception occured While migrated to phr", e);
		}
		log.info("migrated is done {} ", phrRequestPlayLoad.getAbhaAddress());
		return success;
	}

	public String migrate(ShareCMRequestPlayLoad shareCMRequestPlayLoad) {
		log.info("migrate to cm {} ", shareCMRequestPlayLoad);
		String success = "Y";
		try {

			cMMigrationClient.shareCMProfile(shareCMRequestPlayLoad);
		} catch (Exception e) {
			success = "N";
			log.error("Exception occured While migrated to cm", e);
		}
		log.info("migrated is done {} ", shareCMRequestPlayLoad.getPhrAddress());
		return success;

	}

}
