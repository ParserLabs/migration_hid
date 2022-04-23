package com.migration.migration.process;

import java.util.Objects;
import java.util.function.Function;

import com.migration.migration.entity.UserEntity;
import com.migration.migration.request.PhrRequestPlayLoad;
import com.migration.migration.request.PhrUpdatePhotoProfileRequest;

public class HidPhrProfilePhotoTransFormComponent implements Function<UserEntity, PhrUpdatePhotoProfileRequest> {

	@Override
	public PhrUpdatePhotoProfileRequest apply(UserEntity t) {
		return PhrUpdatePhotoProfileRequest.builder()
				                           .healthIdNumber(t.getHealthId())
				                           .healthIdNumber(t.getHealthIdNumber())				                           .profilePhoto(t.getProfilePhoto())
				                           .profilePhotoCompressed(t.isProfilePhotoCompressed())
				                           .build();
	
	}

}
