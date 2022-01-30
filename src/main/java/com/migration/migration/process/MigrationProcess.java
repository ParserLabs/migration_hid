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

import com.migration.migration.entity.KycData;
import com.migration.migration.proxy.MigrationClient;
import com.migration.migration.repository.KycDataRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MigrationProcess {

	@Autowired
	private MigrationClient migrationClient;
	@Autowired
	private KycDataRepository kycDataRepository;

	@Value("${cipher.secretKey}")
	private String aadhaarEncryptionKey;

	private boolean startMigration;
	
	@Autowired
	MigrationProcessHelper migrationProcessHelper;

	public void stop() {
		this.startMigration = false;
	}

	@Async
	public void start() {
		long total = kycDataRepository.count();
		int page = 0;
		int limit = 20;
		long size = 0;
		this.startMigration = true;

		//List<CompletableFuture<KycData>> responseAsync = new ArrayList<>();
		log.info("Total user kyc records in data base are {}", total);
		long start = System.currentTimeMillis();
		log.info("Migration started.");
		while (this.startMigration && total > size) {
			Page<KycData> userKycs = kycDataRepository.findAll(PageRequest.of(page++, limit));
			if (Objects.nonNull(userKycs)) {
				userKycs.stream().filter(Objects::nonNull).filter(userKyc -> !"E".equals(userKyc.getMigrated()))
						.forEach(userKyc -> {
							//responseAsync.add(CompletableFuture.runAsync(() -> migrateUserKycData(userKyc)));
							//CompletableFuture.runAsync(() -> migrateUserKycData(userKyc));
							migrationProcessHelper.migrate(userKyc);
						});
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

		log.info("Migration completed in {} ms.", (System.currentTimeMillis() - start));
		log.info("Total records processed successfully {}.", total);
	}

	@Transactional
	public void migrateUserKycData(KycData kycData) {
		
		 migrationClient.saveUserKyc(kycData);
		// KycData kycDataResponse= null;
		//KycData kycDataResponse = saveUserKycData(kycData);
//		if (Objects.nonNull(kycDataResponse)) {
//			kycData.setMigrated("Y");
//		}
//		} else {
//			kycData.setMigrated("N");
//		}
		// kycDataRepository.save(kycData);
		//return kycData;
	}

	private KycData saveUserKycData(KycData kycData) {
		//String aadhaarNumber = AES.decrypt(kycData.getAadhaar(), aadhaarEncryptionKey);
		//kycData.setAadhaar(aadhaarNumber);
		
		return kycData;
	}

	public static void main(String[] args) {
		String aadhaarNumber = AES.decrypt("AxMuTvV9FncVvJKF0S6SCg==", "NdhMStgeEnv0mnet!@accountN!Haa#");
		System.out.println("aadhaar Number " + aadhaarNumber);
	}
}
