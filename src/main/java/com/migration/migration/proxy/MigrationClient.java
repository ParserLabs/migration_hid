package com.migration.migration.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.migration.migration.config.ClientConfiguration;
import com.migration.migration.entity.KycData;

@FeignClient(value = "migration-client", url = "${user.kyc.service.url}", configuration = ClientConfiguration.class)
public interface MigrationClient {

	@PostMapping
	KycData saveUserKyc(KycData kycData);

}
