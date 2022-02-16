package com.migration.migration.enums;

public enum AccountStatus {
	ACTIVE("ACTIVE"), BLOCKED("BLOCKED"), LOCKED("LOCKED"), CONSENTPENDING("CONSENTPENDING"), 
	DEACTIVATED("DEACTIVATED"), DELETED("DELETED");

	public static boolean isValid(String status) {
		AccountStatus[] values = AccountStatus.values();
		for (AccountStatus auth : values) {
			if (auth.toString().equals(status)) {
				return true;
			}
		}
		return false;
	}

	private String name;

	private AccountStatus(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
