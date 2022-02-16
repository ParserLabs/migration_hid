package com.migration.migration.component;

import java.util.function.Function;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.migration.migration.entity.UserEntity;
import com.migration.migration.request.PhrRequestPlayLoad;

@Component
public class HidPhrTransFormComponent implements Function<UserEntity, PhrRequestPlayLoad> {

	@Override
	public PhrRequestPlayLoad apply(UserEntity userEntity) {
		return PhrRequestPlayLoad.builder().addressLine(userEntity.getAddress())
				.authMethods(userEntity.getAuthMethods()).dayofbirth(userEntity.getDayOfBirth())
				.districtCode(userEntity.getDistrictCode()).email(userEntity.getEmail())
				.emailVerified(StringUtils.hasLength(userEntity.getEmail_verified())
						? Boolean.getBoolean(userEntity.getEmail_verified())
						: false)
				.firstName(userEntity.getFirstName()).fullName(userEntity.getName()).gender(userEntity.getGender())
				.lastName(userEntity.getLastName()).middleName(userEntity.getMiddleName())
				.mobile(userEntity.getMobile())
				.mobileVerified(StringUtils.hasLength(userEntity.getMobile()) ? true : false)
				.kycStatus(String.valueOf(userEntity.isKycVerified())).monthOfBirth(userEntity.getMonthOfBirth())
				.profilePhoto(userEntity.getProfilePhoto() != null ? userEntity.getProfilePhoto().toString()
						: userEntity.getKycPhoto() != null ? userEntity.getKycPhoto().toString() : null)
				.stateCode(userEntity.getStateCode()).status(userEntity.getStatus().name())
				.yearOfBirth(userEntity.getYearOfBirth()).build();
	}
	

}
