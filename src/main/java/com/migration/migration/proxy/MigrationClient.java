package com.migration.migration.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.migration.migration.config.ClientConfiguration;
import com.migration.migration.request.PhrRequestPlayLoad;
import com.migration.migration.request.PhrResponsePlayLoad;

@FeignClient(value = "migration-client", url = "${user.kyc.service.url}", configuration = ClientConfiguration.class)
public interface MigrationClient {

	@PostMapping
	PhrResponsePlayLoad saveUserKyc(PhrRequestPlayLoad phrRequestPlayLoad);

}
