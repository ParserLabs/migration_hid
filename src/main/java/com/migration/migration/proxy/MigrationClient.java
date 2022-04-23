package com.migration.migration.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.migration.migration.config.ClientConfiguration;
import com.migration.migration.request.PhrRequestPlayLoad;
import com.migration.migration.request.PhrResponse;
import com.migration.migration.request.PhrUpdatePhotoProfileRequest;

@FeignClient(value = "migration-client-phr", url = "${phr.id.service.url}", configuration = ClientConfiguration.class)
public interface MigrationClient {

	@PostMapping("/v1/phr/registration/create/phr/migration")
	PhrResponse phrMigration(PhrRequestPlayLoad phrRequestPlayLoad);

	@PostMapping("/v1/phr/registration/update/phr/photo/migration")
	Boolean phrPhotoMigration(PhrUpdatePhotoProfileRequest phrRequestPlayLoad);

}
