package com.migration.migration.request;

import java.util.Set;

import com.migration.migration.enums.AuthMethods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhrRequestPlayLoad {
	private String addressLine;
	private Set<AuthMethods> authMethods;
	private String countryCode;
	private String dayOfbirth;
	private String districtCode;
	private String email;
	private boolean emailVerified;
	private String firstName;
	private String fullName;
	private String gender;
	private String abhaAddress;
	private String kycStatus;
	private String lastName;
	private String middleName;
	private String mobile;
	private boolean mobileVerified;
	private String monthOfBirth;
	private String pinCode;
	private String profilePhoto;
	private String stateCode;
	private String status;
	private String yearOfBirth;
	private boolean notify;
	private String abhaNumber;
}
