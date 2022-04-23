package com.migration.migration.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties
public class PhrUpdatePhotoProfileRequest {

	String phrAddress;
	String healthIdNumber;
	byte[] profilePhoto;
	Boolean profilePhotoCompressed;

	
}
