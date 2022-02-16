package com.migration.migration.constant;

import java.util.Arrays;
import java.util.List;

import java.util.regex.Pattern;

public interface Constants {

	interface DeveloperMessage {
		String ALPHABET_MISSMATCHED = "ALPHABET_MISSMATCHED";
		String FIELD_REQUIRED = "FIELD_REQUIRED";
		String INCORRECT_TYPE = "INCORRECT_TYPE";
		String MOBILE_NUMBER_MISSMATCH = "MOBILE_NUMBER_MISSMATCH";
		String OTP_MISSMATCH = "OTP_MISSMATCH";
		String PASSWORD_MISSMATCH = "PASSWORD_MISSMATCH";
		String PATTREN_MISMATCHED = "PATTREN_MISMATCHED";
		String SIZE_MISMATCHED = "SIZE_MISMATCHED";
		String UUID_MISSMATCH = "UUID_MISSMATCH";
		String DATE_MISSMATCH = "DATE_MISSMATCH";
		String HEALTH_ID = "HEALTH_ID";
		String AADHAAR_NUMBER_INVALID = "AADHAAR_NUMBER_INVALID";
		String AADHAAR_BIOTYPE_INVALID = "AADHAAR_BIOTYPE_INVALID";
		String INVALID_OIDC_REQUEST_CODE = "INVALID_OIDC_REQUEST_CODE";
		String INVALID_NAME_FORMAT = "INVALID_NAME_FORMAT";
		String INVALID_EMAIL_FORMAT = "INVALID_EMAIL_FORMAT";
		String INVALID_DOC_TYPE = "INVALID_DOC_TYPE";
		String GENDER_INVALID = "GENDER_INVALID";
		String DATA_INVALID = "DATA_INVALID";


	}

	interface Endpoints {
		String ACCOUNT_V1_ENDPOINT = "/v1/account";
		String AUTH_V1_ENDPOINT = "/v1/auth";
		String COMMON_V1_ENDPOINT = "/v1/common";
		String LGD_HA_V1 = "/v1/ha/lgd";
		String MOBILE_REGISTRATION_V1_ENDPOINT = "/v1/registration/mobile";
		String AADHAAR_REGISTRATION_V1_ENDPOINT = "/v1/registration/aadhaar";
		String HEALTH_FACILITY_V1_ENDPOINT = "/v1/health/facility";
		String TAGS_HA_V1 = "/v1/ha/tags";
		String SEARCH_V1 ="/v1/search";
		String FORGOT_HEALTH_ID_V1_ENDPOINT = "/v1/forgot/healthId";
		String HID_BENEFIT_V1_ENDPOINT = "/v1/hid/benefit";
		String SEARCH_V2 ="/v2/search";
		String AADHAAR_REGISTRATION_V2_ENDPOINT = "/v2/registration/aadhaar";
		String MOBILE_REGISTRATION_V2_ENDPOINT = "/v2/registration/mobile";
		String AUTH_V2_ENDPOINT = "/v2/auth";
		String ACCOUNT_V2_ENDPOINT = "/v2/account";
		String HEALTH_FACILITY_V2_ENDPOINT = "/v2/health/facility";
		String FORGOT_HEALTH_ID_V2_ENDPOINT = "/v2/forgot/healthId";
		String OIDC_V2_ENDPOINT = "/v2/oidc";
		//EMAIL ACT CONFIRM 
		String EMAIL_ACT_CONFIRMATION_ENDPOINT = "/email/verification/confirmation";
		String IDENTITY_DOCUMENTS_V2_ENDPOINT = "/v2/document";
		//
		String REGISTRATION_OFFLINE_MODE_V2_ENDPOINT = "/v2/registration/aadhaar/hid/offline";
		String HID_BENEFIT_V2_ENDPOINT = "/v2/hid/benefit";
		String LGD_HA_V2 = "/v2/ha/lgd";
		String TAGS_HA_V2 = "/v2/ha/tags";


	}

	interface ValidationPatterns {
		public static final Pattern EMAIL_PATTERN = Pattern.compile(
				"[-a-z0-9~!$%^&*_=+{}\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$/i");

		/**
		 * Health ID can be min of 4 and max of 32 digits alpha numeric. Please add a
		 * check to ensure pure numeric is not allowed. This is to ensure there is no
		 * clash with the 14 digit Health ID number.
		 */
		public static final Pattern HEALTH_ID_PATTERN = Pattern
				.compile("^(?=[a-zA-Z0-9._]{4,32}$)(?!.*[_.]{2})[^_.0-9].*[^_.]$");
	}

	public static final String DISTRICT = "district";
	public static final String GENDER = "gender";
	public static final String HEALTH_ID = "healthId";
	public static final String HEALTH_ID_NUMBER = "healthIdNumber";
	public static final String HEALTH_ID_SUFFIX = "SUFFIX";
	public static final String MOBILE = "mobile";
	public static final String NAME = "name";
	public static final String STATE = "state";
	public static final String X_TOKEN_HEADER = "X-Token";
	public static final String T_TOKEN_HEADER = "T-Token";
	public static final String F_TOKEN_HEADER = "F-Token";
	public static final String TRANSECTION_ID = "txnId";
	public static final String DAY_OF_BIRTH = "dayOfBirth";
	public static final String MONTH_OF_BIRTH = "monthOfBirth";
	public static final String YEAR_OF_BIRTH = "yearOfBirth";
	public static final String ADDRESS = "address";
	public static final String J_AND_K_STATE = "JAMMU AND KASHMIR";
	public static final String LEH_LADAKH="LEH LADAKH";
	public static final String LADAKH="LADAKH";
	public static final String LEH="LEH";
	public static final String KARGIL="KARGIL";
	public static final String CLIENT_ID = "clientId";
	public static final String FROM = "from";
	public static final List<String> LADAKH_DISTRICTS = Arrays.asList(LEH_LADAKH, LADAKH, LEH, KARGIL);
	
	public static final String REQUESTER_ID_HEADER = "requesterId";
	public static final String REQUESTER_TYPE_HEADER = "requesterType";
	public static final String PURPOSE_HEADER = "purpose";
}

