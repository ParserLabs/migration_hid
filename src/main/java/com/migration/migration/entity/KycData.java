package com.migration.migration.entity;


import static javax.persistence.FetchType.LAZY;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_EKYC")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class KycData {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String userEkycId;

	@Column(name = "HEALTH_ID_NUMBER")
	private String healthIdNumber;
	
	@Column(name = "HEALTH_ID")
	private String healthId;

	@Column(name = "PHOTO")
	@Lob
	@Basic(fetch = LAZY)
	private byte[] photo;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "NAME")
	private String name;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "PINCODE")
	private String pincode;

	@Column(name = "DOB_YOB")
	private String birthdate;

	@Column(name = "CO")
	private String careOf;

	@Column(name = "HOUSE")
	private String house;

	@Column(name = "STREET")
	private String street;

	@Column(name = "LM")
	private String landmark;

	@Column(name = "LOC")
	private String locality;

	@Column(name = "VTC")
	private String villageTownCity;

	@Column(name = "SUBDIST")
	private String subDist;

	@Column(name = "DIST")
	private String district;

	@Column(name = "STATE")
	private String state;

	@Column(name = "PO")
	private String postOffice;

	@Column(name = "XML_UID")
	private String signature;

	@Column(name = "AADHAAR_NO")
	private String aadhaar;

	@Column(name = "UIDIA_TXN")
	private String uidiaTxn;

	@Column(name = "ERROR_CODE")
	private String errorCode;

	@Column(name = "REASON")
	private String reason;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "MIGRATED")
	private String migrated;
	
	@Column(name = "KYC_TYPE")
	private String kycType;
	
	@Column(name = "CREATED_DATE")
	@CreatedDate
	private Date createDate;
	
	@Column(name = "MODIFIED_DATE")
	@LastModifiedDate
	private Date modifiedDate;

	@Column(name = "response_code")
	private String responseCode;

	public String getAddress() {
		StringBuilder address = new StringBuilder();

		if (!StringUtils.isEmpty(getCareOf())) {
			address.append("C/O " + getCareOf());
		}

		if (!StringUtils.isEmpty(getHouse())) {
			address.append(" " + getHouse());
		}
		if (!StringUtils.isEmpty(getStreet())) {
			address.append(" " + getStreet());
		}

		if (!StringUtils.isEmpty(getLandmark())) {
			address.append(" " + getLandmark());
		}

		if (!StringUtils.isEmpty(getLocality())) {
			address.append(" " + getLocality());
		}

		if (!StringUtils.isEmpty(getVillageTownCity())) {
			address.append(" " + getVillageTownCity());
		}

		if (!StringUtils.isEmpty(getSubDist())) {
			address.append(" " + getSubDist());
		}

		return address.toString();
	}

}

