package com.migration.migration.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class to represent JWT token as response.
 * 
 * 
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhrResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;

	private String token;

	long expiresIn;

	private String refreshToken;

	long refreshExpiresIn;

	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private String phrAdress;

	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	private String firstName;
}