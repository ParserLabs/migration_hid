package com.migration.migration.component;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.migration.migration.entity.UserEntity;
import com.migration.migration.request.ShareCMRequestPlayLoad;

public class HidCMTransformComponent {

	public ShareCMRequestPlayLoad transfromUserEntityToShareCM(UserEntity userEntity) {
		
		ShareCMRequestPlayLoad shareCMRequestPlayLoad = null;
		String phrAddress = fetchPhrAddress(userEntity);
		
		if (StringUtils.hasLength(phrAddress)) {
			shareCMRequestPlayLoad = new ShareCMRequestPlayLoad();
			shareCMRequestPlayLoad.setHealthIdNumber(userEntity.getHealthIdNumber());
			shareCMRequestPlayLoad.setHealthId(userEntity.getHealthId());
			shareCMRequestPlayLoad.setMobile(userEntity.getMobile());
			shareCMRequestPlayLoad.setFirstName(userEntity.getFirstName());
			shareCMRequestPlayLoad.setMiddleName(userEntity.getMiddleName());
			shareCMRequestPlayLoad.setLastName(userEntity.getLastName());
			shareCMRequestPlayLoad.setName(userEntity.getName());
			shareCMRequestPlayLoad.setYearOfBirth(userEntity.getYearOfBirth());
			shareCMRequestPlayLoad.setDayOfBirth(userEntity.getDayOfBirth());
			shareCMRequestPlayLoad.setMonthOfBirth(userEntity.getMonthOfBirth());
			shareCMRequestPlayLoad.setGender(userEntity.getGender());
			shareCMRequestPlayLoad.setStateCode(userEntity.getStateCode());
			shareCMRequestPlayLoad.setDistrictCode(userEntity.getDistrictCode());
			shareCMRequestPlayLoad.setPincode(userEntity.getPincode());
			shareCMRequestPlayLoad.setAddress(userEntity.getAddress());
			shareCMRequestPlayLoad.setStateName(userEntity.getStateName());
			shareCMRequestPlayLoad.setDistrictName(userEntity.getDistrictName());
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

}
