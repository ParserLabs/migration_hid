package com.migration.migration.process;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.migration.migration.component.HidCMTransformComponent;
import com.migration.migration.component.HidPhrTransFormComponent;
import com.migration.migration.entity.UserEntity;
import com.migration.migration.proxy.MigrationClient;
import com.migration.migration.repository.UserEntityDataRepository;
import com.migration.migration.request.PhrRequestPlayLoad;
import com.migration.migration.request.PhrUpdatePhotoProfileRequest;
import com.migration.migration.request.ShareCMRequestPlayLoad;
import com.migration.migration.utils.ImageUtils;

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
	private HidPhrProfilePhotoTransFormComponent hidPhrProfilePhotoTransFormComponent;
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
			List<Object> userKycs = userEntityDataRepository.findHealthIdNumberAndPhrAddress(offset, batchSize);
				
			offset += batchSize;
			
			if (Objects.nonNull(userKycs)) {
				userKycs.stream().forEach(user -> {
					try {
						String healthIdNumber =  transformPhoto(user).get(1);
						String phrAddress      =  transformPhoto(user).get(0);
						UserEntity userEntity = userEntityDataRepository.getUserLiteWithPhotoByHealthIdNumber(healthIdNumber);
						userEntity.setHealthId(phrAddress);
						transform(userEntity);
					    
						
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


	private void transform(UserEntity userEntity) throws InterruptedException {
		// TODO Auto-generated method stub
		 //UserEntity userEntObject = hidCMTransformComponent.transform(profilePhoto);
		//userEntObject.setHealthId(phrAddress);
		if (Objects.nonNull(userEntity.getProfilePhoto())) {
			if (userEntity.isProfilePhotoCompressed()) {
				userEntity.setProfilePhoto(ImageUtils.decompress(userEntity.getProfilePhoto()));
			}
		 }
		newmigrationProcess(userEntity);
	}

	private String bytesToString(byte[] bytes) {
		return ArrayUtils.isEmpty(bytes) ? null : new String(bytes);
	}
	
	private UserEntity transform(Object object) throws InterruptedException {
	
		return migrationProcess(hidCMTransformComponent.transform(object));
	
	}

	private List<String> transformPhoto(Object object)  {
		
		return hidCMTransformComponent.tranformHealthIdObject(object);
	
	}

	private void newmigrationProcess(UserEntity userEntity) throws InterruptedException {
		
		
		PhrUpdatePhotoProfileRequest phrRequestPlayLoad = hidPhrProfilePhotoTransFormComponent.apply(userEntity);
		
		
				migrationProcessHelper.migrate(phrRequestPlayLoad)
				                            .thenAccept(phrMigrateStatus -> {
				                            	
				                           userEntityDataRepository.update_dto_profile_photo (
				                        		                                       phrMigrateStatus,
				                        		                                       phrRequestPlayLoad.getHealthIdNumber());
				                            });
		}


	
	private UserEntity migrationProcess(UserEntity userEntity) throws InterruptedException {
		if( StringUtils.hasLength(userEntity.getMobile()) && userEntity.getMobile().length() > 10 )
		{
			String text = userEntity.getMobile();
			userEntity.setMobile(text.substring(text.length() - 10, text.length()) );	
		}
		
		if (!StringUtils.hasLength(userEntity.getStateCode()) || !StringUtils.hasLength(userEntity.getDistrictCode()) || !NumberUtils.isNumber(userEntity.getStateCode()) || !NumberUtils.isNumber(userEntity.getDistrictCode())  )
		{
			userEntity.setStateCode(null);
			userEntity.setDistrictCode(null);			
		
	    }
	
		if (StringUtils.hasLength(userEntity.getFirstName()) && userEntity.getFirstName().length() < 1)
		{
			if (StringUtils.hasLength(userEntity.getName()) && userEntity.getFirstName().length() < 1)
			{
				return userEntity;
			}
			else
			{
				userEntity.setFirstName(userEntity.getName());			
			}	
		}	
		
		PhrRequestPlayLoad phrRequestPlayLoad = hidPhrTransFormComponent.apply(userEntity);
		ShareCMRequestPlayLoad shareCMRequestPlayLoad = hidCMTransformComponent
				.transfromUserEntityToShareCM(userEntity);
		
		if (!"Y".equalsIgnoreCase(userEntity.getPhrMigrated()))
		{	
		migrationProcessHelper.migrate(phrRequestPlayLoad)
				                            .thenAccept(phrMigrateStatus -> {
				                            	
				                           userEntityDataRepository.updateAbhaAccoutsForPHR(
				                        		                                       phrMigrateStatus,
				                        		                                       phrRequestPlayLoad.getAbhaNumber()
				                        		                                     );
				                            });
		}
//		if (!"Y".equalsIgnoreCase(userEntity.getCmMigrated()))
//		{
//		migrationProcessHelper.migrate(shareCMRequestPlayLoad)
//				                            .thenAccept(cmMigrateStatus -> {
//				                            	
//				                        		userEntityDataRepository.updateAbhaAccoutsForCM(cmMigrateStatus, shareCMRequestPlayLoad.getHealthIdNumber());				                            	
//				                            });
//		}

		return userEntity;
	}

	public static void main(String[] args) {
		String aadhaarNumber = AES.decrypt("AxMuTvV9FncVvJKF0S6SCg==", "NdhMStgeEnv0mnet!@accountN!Haa#");
		System.out.println("aadhaar Number " + aadhaarNumber);
	}
}
