package com.migration.migration.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.migration.migration.component.HidCMTransformComponent;
import com.migration.migration.component.HidPhrTransFormComponent;
import com.migration.migration.entity.KycData;
import com.migration.migration.entity.UserEntity;
import com.migration.migration.proxy.MigrationClient;
import com.migration.migration.repository.UserEntityDataRepository;
import com.migration.migration.request.PhrRequestPlayLoad;
import com.migration.migration.request.ShareCMRequestPlayLoad;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MigrationProcess {

	@Autowired
	private MigrationClient migrationClient;
	@Autowired
	private UserEntityDataRepository userEntityDataRepository;

	@Value("${cipher.secretKey}")
	private String aadhaarEncryptionKey;

	private boolean startMigration;

	@Autowired
	private MigrationProcessHelper migrationProcessHelper;
	@Autowired
	private HidPhrTransFormComponent hidPhrTransFormComponent;
	@Autowired
	private HidCMTransformComponent hidCMTransformComponent;

	public void stop() {
		this.startMigration = false;
	}

	@Async
	public void start() {
		long total = userEntityDataRepository.count();
		int page = 0;
		int limit = 20;
		long size = 0;
		this.startMigration = true;
		long offset = 1;

		log.info("Total user kyc records in data base are {}", total);
		long start = System.currentTimeMillis();
		log.info("Migration started.");
		while (this.startMigration && total > size) {
			List<Object> userKycs = userEntityDataRepository.findAbhaAccounts(offset, limit);
			offset += limit;
			if (Objects.nonNull(userKycs)) {
				userKycs.stream().forEach(user -> {
					transform(user);
				});

			}

			size += limit;
			 log.info("Total records processed till now {}.", size);
		}
		log.info("{} records processed successfully.", size);
	}

//		responseAsync.parallelStream().forEach(response -> {
//			try {
//				response.get();
//			} catch (InterruptedException | ExecutionException e) {
//				log.info("Exception occured while migration.");
//			}
//		});

	// log.info("Migration completed in {}
	// ms.",(System.currentTimeMillis()-start));log.info("Total records processed
	// successfully {}.",total);

//	@Transactional
//	public void migrateUserKycData(UserEntity userEntity) {
//		
//		 migrationClient.saveUserKyc(userEntity);
//		 KycData kycDataResponse= null;
//		if (Objects.nonNull(kycDataResponse)) {
//			userEntity.setPhrMigrated("Y");
//		} else if () {
//			userEntity.setCmMigrated("Y");
//		}
//		
//		 userEntityDataRepository.save(kycData);
//		return kycData;
//	}

	private UserEntity transform(Object object) {
		return migrationProcess(hidCMTransformComponent.transform(object));
	}

	private UserEntity migrationProcess(UserEntity userEntity) {

		PhrRequestPlayLoad phrRequestPlayLoad = hidPhrTransFormComponent.apply(userEntity);
		String phrMigrate = migrationProcessHelper.migrate(phrRequestPlayLoad);
		userEntityDataRepository.updateAbhaAccouts("N", phrMigrate, phrRequestPlayLoad.getHealthIdNumber());
		ShareCMRequestPlayLoad shareCMRequestPlayLoad = hidCMTransformComponent
				.transfromUserEntityToShareCM(userEntity);
		String cmMigrate = migrationProcessHelper.migrate(shareCMRequestPlayLoad);
		userEntityDataRepository.updateAbhaAccouts(cmMigrate, "N", shareCMRequestPlayLoad.getHealthIdNumber());
		return userEntity;
	}

	public static void main(String[] args) {
		String aadhaarNumber = AES.decrypt("AxMuTvV9FncVvJKF0S6SCg==", "NdhMStgeEnv0mnet!@accountN!Haa#");
		System.out.println("aadhaar Number " + aadhaarNumber);
	}
}
