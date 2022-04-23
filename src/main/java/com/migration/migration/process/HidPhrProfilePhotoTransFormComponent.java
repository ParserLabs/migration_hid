package com.migration.migration.process;

import java.util.Objects;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.migration.migration.entity.UserEntity;
import com.migration.migration.request.PhrRequestPlayLoad;
import com.migration.migration.request.PhrUpdatePhotoProfileRequest;

@Component
public class HidPhrProfilePhotoTransFormComponent implements Function<UserEntity, PhrUpdatePhotoProfileRequest> {

	@Override
	public PhrUpdatePhotoProfileRequest apply(UserEntity t) {
		return PhrUpdatePhotoProfileRequest.builder()
				                           .phrAddress(t.getHealthId())
				                           .healthIdNumber(t.getHealthIdNumber())				                           .profilePhoto(t.getProfilePhoto())
				                           .profilePhotoCompressed(t.isProfilePhotoCompressed())
				                           .profilePhoto(t.getProfilePhoto())
				                           .build();
	
	}

}
