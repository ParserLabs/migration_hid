package com.migration.migration.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShareCMRequestPlayLoad {
	
	private String healthIdNumber;
	
	private String healthId;
	
	private String mobile;

	private String firstName;

	private String middleName;
	
	private String lastName;
	
	private String name;
	
	private String yearOfBirth;
	
	private String dayOfBirth;
	
	private String monthOfBirth;
	
	private String gender;
	
	private String stateCode;
	
	private String districtCode;
	
	private String pincode;
	
	private String address;
	
	private String stateName;
	
	private String districtName;
}