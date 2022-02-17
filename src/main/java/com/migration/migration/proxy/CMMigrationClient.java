package com.migration.migration.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.migration.migration.config.ClientConfiguration;
import com.migration.migration.request.ShareCMRequestPlayLoad;

@FeignClient(value = "migration-client-cm", url = "${cm.id.service.url}", configuration = ClientConfiguration.class)
public interface CMMigrationClient {
	
	@PostMapping
	void shareCMProfile(ShareCMRequestPlayLoad shareCMRequestPlayLoad);


}
