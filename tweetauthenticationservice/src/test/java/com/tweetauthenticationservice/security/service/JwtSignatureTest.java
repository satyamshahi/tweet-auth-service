/**
 * 
 */
package com.tweetauthenticationservice.security.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.tweetauthenticationservice.security.service.JwtSignature;
//import com.tweetauthenticationservice.security.service.com;

/**
 *This is JwtSignature Test class
 *
 */
class JwtSignatureTest {

	/**
	 * Test method for
	 * {@link com.tweetauthenticationservice.security.service.JwtSignature#getJwtSecret()}.
	 */
	@Test
	 void testGetJwtSecret() {
		final JwtSignature jwtSignature = new JwtSignature();
		 jwtSignature.setJwtSecret("secret");
		assertEquals("secret", jwtSignature.getJwtSecret());
	}

	/**
	 * Test method for
	 * {@link com.tweetauthenticationservice.security.service.JwtSignature#getJwtExpirationMs()}.
	 */
	@Test
	 void testGetJwtExpirationMs() {
		final JwtSignature jwtSignature = new JwtSignature();
		jwtSignature.setJwtExpirationMs(500);
		assertEquals(500, jwtSignature.getJwtExpirationMs());
	}

}
