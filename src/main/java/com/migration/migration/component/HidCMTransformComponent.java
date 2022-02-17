package com.migration.migration.component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.migration.migration.entity.UserEntity;
import com.migration.migration.enums.AccountStatus;
import com.migration.migration.enums.AuthMethods;
import com.migration.migration.request.ShareCMRequestPlayLoad;

@Component
public class HidCMTransformComponent {

	public ShareCMRequestPlayLoad transfromUserEntityToShareCM(UserEntity userEntity) {

		ShareCMRequestPlayLoad shareCMRequestPlayLoad = null;
		if (StringUtils.hasLength(userEntity.getHealthId())) {
			shareCMRequestPlayLoad = new ShareCMRequestPlayLoad();
			shareCMRequestPlayLoad.setHealthIdNumber(userEntity.getHealthIdNumber());
			shareCMRequestPlayLoad.setPhrAddress(userEntity.getHealthId());
			shareCMRequestPlayLoad.setMobile(userEntity.getMobile());
			shareCMRequestPlayLoad.setFirstName(userEntity.getFirstName());
			shareCMRequestPlayLoad.setMiddleName(userEntity.getMiddleName());
			shareCMRequestPlayLoad.setLastName(userEntity.getLastName());
			shareCMRequestPlayLoad.setYearOfBirth(userEntity.getYearOfBirth());
			shareCMRequestPlayLoad.setDayOfBirth(userEntity.getDayOfBirth());
			shareCMRequestPlayLoad.setMonthOfBirth(userEntity.getMonthOfBirth());
			shareCMRequestPlayLoad.setGender(userEntity.getGender());
			shareCMRequestPlayLoad.setStateCode(userEntity.getStateCode());
			shareCMRequestPlayLoad.setDistrictCode(userEntity.getDistrictCode());
			shareCMRequestPlayLoad.setPinCode(userEntity.getPincode());
			shareCMRequestPlayLoad.setAddress(userEntity.getAddress());
			
		}

		return shareCMRequestPlayLoad;
	}

	public String fetchPhrAddress(UserEntity userEntity) {

		String phrAddress = null;
		if (!CollectionUtils.isEmpty(userEntity.getHidPhrAddressEntity())) {

			phrAddress = userEntity.getHidPhrAddressEntity().stream()
					.filter(data -> !StringUtils.isEmpty(data.getPhrAddress())
							&& data.getStatus().equalsIgnoreCase("ACTIVE") && data.getPreferred() == 1)
					.map(data -> data.getPhrAddress()).findFirst().orElse(null);
		}
		return phrAddress;
	}

	public UserEntity transform(Object object) {
		Object[] objects = (Object[]) object;
		return UserEntity.builder().healthIdNumber(isNotEmpty(objects[0])).healthId(isNotEmpty(objects[1]))
				.password(isNotEmpty(objects[2])).name(isNotEmpty(objects[3])).firstName(isNotEmpty(objects[4]))
				.lastName(isNotEmpty(objects[5])).middleName(isNotEmpty(objects[6])).mobile(isNotEmpty(objects[7]))
				.email(isNotEmpty(objects[8])).gender(isNotEmpty(objects[9])).monthOfBirth(isNotEmpty(objects[10]))
				.dayOfBirth(isNotEmpty(objects[11])).yearOfBirth(isNotEmpty(objects[12]))
				.profilePhoto(photo(objects[13])).kycPhoto(photo(objects[14]))
				.status(AccountStatus.valueOf(isNotEmpty(objects[15])))
				.createdDate(localDateFromTimestamp((Timestamp) objects[16])).clientId(isNotEmpty(objects[17]))
				// .updateDate(convertLocalDateTime(isNotEmpty(objects[18])))
				.lastUpdatedBy(isNotEmpty(objects[19])).stateCode(isNotEmpty(objects[20]))
				.districtCode(isNotEmpty(objects[21])).address(isNotEmpty(objects[22]))
				.authMethods(authMethod(isNotEmpty(objects[23]))).build();

	}

	public LocalDateTime localDateFromTimestamp(Timestamp timestamp) {
		return LocalDateTime.ofInstant(timestamp.toInstant(), ZoneOffset.ofHours(0));
	}

	public String isNotEmpty(Object object) {
		return !Objects.isNull(object) ? object.toString() : "";

	}

	public byte[] photo(Object object) {

		return !Objects.isNull(object) ? object.toString().getBytes() : null;
	}

	public LocalDateTime convertLocalDateTime(String date) {
		return LocalDateTime.parse(date);
	}

	public Set<AuthMethods> authMethod(String authMethod) {
		Set<AuthMethods> methods = null;
		if (StringUtils.hasLength(authMethod)) {
			List<String> authMethods = Arrays.asList(authMethod.split(","));
			methods = authMethods.stream().map(auth -> AuthMethods.valueOf(auth)).collect(Collectors.toSet());
		}

		return methods;

	}

}
