package com.migration.migration.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "hid_phr_address", uniqueConstraints = { @UniqueConstraint(name = "hpa_Unique_hid_phr", columnNames = {
		"health_id_number", "phr_address" }) }, indexes = {
				@Index(name = "hid_index", columnList = "health_id_number, status"),
				@Index(name = "phr_index", columnList = "phr_address, status"),
				@Index(name = "phrPre_index", columnList = "phr_address, status, preferred") })

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(of = { "phrAddress" }, callSuper=false)
public class HidPhrAddressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hid_phr_address_id")
	private BigInteger hidPhrAddressId;

	@ManyToOne
	@JoinColumn(name = "health_id_number", nullable = false)
	private UserEntity userEntity;

	@Column(name = "status", columnDefinition = "varchar(20) default 'ACTIVE'")
	private String status;

	@Column(name = "phr_address", unique = true)
	private String phrAddress;

	@Column(name = "preferred", columnDefinition = "int default '0'")
	private int preferred;

	@Column(name = "has_migrated", columnDefinition = "varchar(2) default 'n'")
	private String hasMigrated;
	
	

//	@PrePersist
//	public void prePersist() {
//		
//		String healthIdSuffix = System.getProperty(HEALTH_ID_SUFFIX, "@ndhm");
//		if (!GeneralUtils.isBlank(phrAddress)) {
//			if (!phrAddress.contains("@")) {
//				phrAddress = phrAddress + healthIdSuffix;
//			} }
//	}

//	@PreUpdate
//	public void preUpdate() {
//		
//		String healthIdSuffix = System.getProperty(HEALTH_ID_SUFFIX, "@ndhm");
//		if (!GeneralUtils.isBlank(phrAddress)) {
//			if (!phrAddress.contains("@")) {
//				phrAddress = phrAddress + healthIdSuffix;
//			} 
//		}
//	}

}
