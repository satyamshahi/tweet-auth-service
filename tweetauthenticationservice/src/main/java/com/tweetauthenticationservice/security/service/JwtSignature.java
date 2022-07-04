package com.tweetauthenticationservice.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * This class use to get jwtSecret and jwtExpiration from application.property
 * file
 *
 */
@Service
public class JwtSignature {

	/**
	 * jwtSecret loaded from application.property file
	 */
	@Value("${jwt.resource.jwtSecret}")
	private String jwtSecret;

	/**
	 * jwtExpiration time is loaded from application.property file
	 */
	@Value("${jwt.resource.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String getJwtSecret() {
		return jwtSecret;
	}

	public int getJwtExpirationMs() {
		return jwtExpirationMs;
	}

	public void setJwtSecret(final String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}

	public void setJwtExpirationMs(final int jwtExpirationMs) {
		this.jwtExpirationMs = jwtExpirationMs;
	}

}
