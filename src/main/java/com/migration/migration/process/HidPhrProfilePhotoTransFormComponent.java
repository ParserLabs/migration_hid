package com.migration.migration.process;

import java.util.function.Function;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import com.migration.migration.entity.UserEntity;
import com.migration.migration.request.PhrUpdatePhotoProfileRequest;

@Component
public class HidPhrProfilePhotoTransFormComponent implements Function<UserEntity, PhrUpdatePhotoProfileRequest> {

	@Override
	public PhrUpdatePhotoProfileRequest apply(UserEntity t) {
		return PhrUpdatePhotoProfileRequest.builder()
				                           .phrAddress(t.getHealthId())
				                           .healthIdNumber(t.getHealthIdNumber())				                           
				                           .profilePhotoCompressed(t.isProfilePhotoCompressed())
				                           .profilePhoto(bytesToString(t.getProfilePhoto()))
				                           .build();
	
	}

	
	private String bytesToString(byte[] bytes) {
		return ArrayUtils.isEmpty(bytes) ? null : new String(bytes);
	}
}
