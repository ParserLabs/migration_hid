package com.migration.migration.enums;

import java.util.ArrayList;
import java.util.List;

public enum AuthMethods {
	AADHAAR_OTP("AADHAAR_OTP"), MOBILE_OTP("MOBILE_OTP"), 
	PASSWORD("PASSWORD"), DEMOGRAPHICS("DEMOGRAPHICS"),
	AADHAAR_BIO("AADHAAR_BIO");
	public static List<String> getAllSupportedAuth() {
		List<String> names = new ArrayList<>();
		for (AuthMethods auth : values()) {
			names.add(auth.name);
		}
		return names;
	}

	public static boolean isValid(String authType) {
		AuthMethods[] values = AuthMethods.values();
		for (AuthMethods auth : values) {
			if (auth.toString().equals(authType)) {
				return true;
			}
		}
		return false;
	}

	private String name;

	private AuthMethods(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}