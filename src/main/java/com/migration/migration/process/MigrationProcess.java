package com.migration.migration.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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

	
	@Value("${total.no.records.send:100}")
	private int totalRecords;
	
	@Value("${record.offset:0}")
	private long offset;
	
	@Value("${migration.batch.size:100}")
	private int batchSize;

	
	public void stop() {
		this.startMigration = false;
	}

	@Async
	public void start() throws InterruptedException {
		long size = 0;
		this.startMigration = true;

		log.info("Total user kyc records in data base are {}", totalRecords);
		long start = System.currentTimeMillis();
		log.info("Migration started.");
		while (this.startMigration && totalRecords > size) {
			List<Object> userKycs = userEntityDataRepository.findAbhaAccounts(offset, batchSize);
				
			offset += batchSize;
			
			if (Objects.nonNull(userKycs)) {
				userKycs.stream().forEach(user -> {
					try {
						transform(user);
					    
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

			}
			TimeUnit.SECONDS.sleep(5);			
			size += batchSize;
			 log.info("Total records processed till now {}.", size);
		}
		log.info("{} records processed successfully.", size);
	}


	private UserEntity transform(Object object) throws InterruptedException {
	
		return migrationProcess(hidCMTransformComponent.transform(object));
	
	}

	private UserEntity migrationProcess(UserEntity userEntity) throws InterruptedException {

		PhrRequestPlayLoad phrRequestPlayLoad = hidPhrTransFormComponent.apply(userEntity);
		ShareCMRequestPlayLoad shareCMRequestPlayLoad = hidCMTransformComponent
				.transfromUserEntityToShareCM(userEntity);
		
		migrationProcessHelper.migrate(phrRequestPlayLoad)
				                            .thenAccept(phrMigrateStatus -> {
				                           userEntityDataRepository.updateAbhaAccoutsForPHR(
				                        		                                       phrMigrateStatus,
				                        		                                       phrRequestPlayLoad.getAbhaNumber()
				                        		                                     );
				                            });
		
		migrationProcessHelper.migrate(shareCMRequestPlayLoad)
				                            .thenAccept(cmMigrateStatus -> {
				                        		userEntityDataRepository.updateAbhaAccoutsForCM(cmMigrateStatus, shareCMRequestPlayLoad.getHealthIdNumber());				                            	
				                            });

		return userEntity;
	}

	public static void main(String[] args) {
		String aadhaarNumber = AES.decrypt("AxMuTvV9FncVvJKF0S6SCg==", "NdhMStgeEnv0mnet!@accountN!Haa#");
		System.out.println("aadhaar Number " + aadhaarNumber);
	}
}
