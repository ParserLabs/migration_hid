
package com.migration.migration.entity;
import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.migration.migration.enums.AccountStatus;
import com.migration.migration.enums.AuthMethods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {
    
	@Id
	@GeneratedValue(generator = "luhn-generator")
	@GenericGenerator(name = "luhn-generator", strategy = "com.migration.migration.component.HealthIdGenerator")
	private String healthIdNumber;

	@Column(name = "origin")
	private String clientId;

	@Column(name = "lst_updated_by")
	private String lastUpdatedBy;

	@Column(name = "hip_id")
	private String xhipId;

	@Column(name = "facility_id")
	private String facilityId;

	@Transient
	private KycData kycData;

	@Column
	private String address;

	@ElementCollection(targetClass = AuthMethods.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "account_auth_methods", joinColumns = @JoinColumn(name = "healthIdNumber"))
	@Enumerated(EnumType.STRING)
	private Set<AuthMethods> authMethods = new HashSet<>();

	@CreatedDate
	@Column(name = "created_date", nullable = false, updatable = false)
	private LocalDateTime createdDate;

	@Column(name = "kyc_verified")
	private boolean kycVerified;

	@Column(name = "okyc_verified", columnDefinition = "boolean default false")
	private boolean ortherKycVerified;

	@Column(name = "profile_photo_compressed", columnDefinition = "boolean default false")
	private boolean profilePhotoCompressed;

	@Column
	private String dayOfBirth;

	@Column
	private String districtCode;

	@Column
	private String districtName;

	@Column
	private String email;

	@Column
	private String firstName;

	@Column
	private String gender;

	@Column(unique = true)
	private String healthId;

	@Column(name = "kycDOB")
	private String kycDobYob;

	@Basic(fetch = LAZY)
	@Lob
	@Column(name = "kycPhoto")
	private byte[] kycPhoto;

	@Column
	private String lastName;

	@Column
	private String middleName;

	@Column
	private String mobile;

	@Column
	private String monthOfBirth;

	@Column
	private String name;

	@Column
	@JsonIgnore
	private String password;

	@Column
	private String pincode;

	@Basic(fetch = LAZY)
	@Lob
	@Column(name = "profilePhoto")
	private byte[] profilePhoto;

	@Column
	private String stateCode;

	@Column
	private String stateName;

	@Column
	@Enumerated(EnumType.STRING)
	private AccountStatus status;

	@Column
	private String subDistrictCode;

	@Column
	private String subdistrictName;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "account_tags", joinColumns = @JoinColumn(name = "healthIdNumber"))
	private Map<String, String> tags = new HashMap<>();

	@Column
	private String townCode;

	@Column
	private String townName;

	@Column(name = "consent_date ", nullable = true)
	private LocalDateTime consentDate;

	@LastModifiedDate
	@Column(name = "update_date", nullable = true, updatable = true)
	private LocalDateTime updateDate;

	@Column
	private String villageCode;

	@Column
	private String villageName;

	@Column
	private String wardCode;

	@Column
	private String wardName;

	@Column(unique = true)
	private String xmlUID;

	@Column
	private String yearOfBirth;

	@Column
	private String email_verified;

	@Column(name = "email_verification_date", nullable = true, updatable = true)
	private LocalDateTime email_verification_date;

	@Column(name = "verification_status")
	private String verificationStatus;

	@Column(name = "verification_type")
	private String verificationType;

	@Column(name = "document_code")
	private String documentCode;
	
	@Column(name = "consent_version")
	private String consentVersion;
	
	@Column(name = "cm_migrated")
	private String cmMigrated;
	
	@Column(name = "phr_migrated")
	private String phrMigrated;

	
	@OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	Set<HidPhrAddressEntity> hidPhrAddressEntity= new HashSet<HidPhrAddressEntity>();

	public UserEntity(String healthIdNumber, String healthId, String password) {
		this.healthIdNumber = healthIdNumber;
		this.healthId = healthId;
		this.password = password;
	}

	public UserEntity(String healthIdNumber, String healthId, String name, String mobile) {
		this.healthIdNumber = healthIdNumber;
		this.healthId = healthId;
		this.name = name;
		this.mobile = mobile;
	}

	public UserEntity(String healthIdNumber, String healthId, String name, String mobile, String gender, String address,
			String dayOfBirth, String monthOfBirth, String yearOfBirth, String districtCode, String stateCode,
			String districtName, String stateName) {
		this.healthIdNumber = healthIdNumber;
		this.healthId = healthId;
		this.name = name;
		this.mobile = mobile;
		this.gender = gender;
		this.address = address;
		this.yearOfBirth = yearOfBirth;
		this.dayOfBirth = dayOfBirth;
		this.monthOfBirth = monthOfBirth;
		this.districtCode = districtCode;
		this.stateCode = stateCode;
		this.districtName = districtName;
		this.stateName = stateName;
	}

	public UserEntity(String healthIdNumber, String healthId, String name, String mobile, String gender, String address,
			String dayOfBirth, String monthOfBirth, String yearOfBirth, String districtCode, String stateCode,
			String districtName, String stateName, byte[] profilePhoto, boolean profilePhotoCompressed) {
		this.healthIdNumber = healthIdNumber;
		this.healthId = healthId;
		this.name = name;
		this.mobile = mobile;
		this.gender = gender;
		this.address = address;
		this.yearOfBirth = yearOfBirth;
		this.dayOfBirth = dayOfBirth;
		this.monthOfBirth = monthOfBirth;
		this.districtCode = districtCode;
		this.stateCode = stateCode;
		this.districtName = districtName;
		this.stateName = stateName;
		this.profilePhoto = profilePhoto;
		this.profilePhotoCompressed = profilePhotoCompressed;
	}

//	@PrePersist
//	public void prePersist() {
//		// Copy the kycPhoto into profile photo if available first time.
//		if ((kycPhoto != null && kycPhoto.length > 0) && profilePhoto == null) {
//			profilePhoto = kycPhoto;
//		}
//
//		// Save the client id every insert
//		clientId = HealthIdContextHolder.clientId();
//		xhipId = HealthIdContextHolder.xHipId();
//		facilityId = HealthIdContextHolder.facilityId();
//		lastUpdatedBy = clientId;
//
//		String healthIdSuffix = System.getProperty(HEALTH_ID_SUFFIX, "@ndhm");
//		if (!GeneralUtils.isBlank(healthId)) {
//			if (!healthId.contains("@")) {
//				healthId = healthId + healthIdSuffix;
//			} else if (!healthId.endsWith(healthIdSuffix)) {
//				healthId = healthId.split("@")[0];
//				healthId = healthId + healthIdSuffix;
//			}
//		}
//	}

//	@PreUpdate
//	public void preUpdate() {
//		lastUpdatedBy = HealthIdContextHolder.clientId();
//		String healthIdSuffix = System.getProperty(HEALTH_ID_SUFFIX, "@ndhm");
//		if (!GeneralUtils.isBlank(healthId)) {
//			if (!healthId.contains("@")) {
//				healthId = healthId + healthIdSuffix;
//			} else if (!healthId.endsWith(healthIdSuffix)) {
//				healthId = healthId.split("@")[0];
//				healthId = healthId + healthIdSuffix;
//			}
//		}
//	}

}