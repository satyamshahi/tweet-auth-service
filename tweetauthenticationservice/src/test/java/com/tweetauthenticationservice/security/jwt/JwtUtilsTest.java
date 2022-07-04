/**
 * 
 */
package com.tweetauthenticationservice.security.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.tweetauthenticationservice.payload.ValidationResponse;
import com.tweetauthenticationservice.security.jwt.JwtUtils;
//import com.tweetauthenticationservice.security.jwt.com;
import com.tweetauthenticationservice.security.service.JwtSignature;

/**
 * JwtUtils Testing class
 *
 */
class JwtUtilsTest {

	/**
	 * 
	 */
	@InjectMocks
	public transient JwtUtils jwtUtils;

	/**
	 * 
	 */
	@Mock
	public transient JwtSignature jwtSignature;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);

	}

	/**
	 * Test method for
	 * {@link com.authenticationmicroservice.security.jwt
.	 *	JwtUtils#generateJwtToken(org.springframework.security.
	 *	core.Authentication)}.
	 */
	@Test
	void testGenerateJwtToken() {

		when(jwtSignature.getJwtExpirationMs()).thenReturn(900000);

		when(jwtSignature.getJwtSecret()).thenReturn("secret");

		final String result = jwtUtils.generateJwtToken("satyam1");

		assertNotNull(result);
	}

	/**
	 * Test method for
	 * {@link com.authenticationmicroservice.security.
	 * jwt.JwtUtils#getUserNameFromJwtToken(java.lang.String)}.
	 */

	@Test
	void testGetUserNameFromJwtToken() {

		when(jwtSignature.getJwtExpirationMs()).thenReturn(900000);

		when(jwtSignature.getJwtSecret()).thenReturn("SecretKey");

		final String result1 = jwtUtils.generateJwtToken("satyam1");

		when(jwtSignature.getJwtSecret()).thenReturn("SecretKey");

		final String result = jwtUtils.getUserNameFromJwtToken(result1);
		assertNotNull(result);

	}

	/**
	 * Test method for
	 * {@link com.authenticationmicroservice.security
	 * .jwt.JwtUtils#validateJwtToken(java.lang.String)}.
	 */
	@Test
	void testValidateJwtToken() {
		when(jwtSignature.getJwtExpirationMs()).thenReturn(900000);

		when(jwtSignature.getJwtSecret()).thenReturn("SecretKey");

		final String result = jwtUtils.generateJwtToken("satyam1");

		assertEquals(true, jwtUtils.validateJwtToken(result).getBody().getMessage().equals("Validated Successfully...."));

	}

	/**
	 * Test method for
	 * {@link com.authenticationmicroservice.security.jwt
	 * .JwtUtils#validateJwtToken(java.lang.String)}.
	 */
	@Test
	void testValidateJwtTokenThrowsSignatureException() {

		final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
				+ "eyJ1c2VybmFtZSI6InNhdHlhbTEiLCJwYXNzd29yZCI6IjEyMzQ1In0"
				+ ".iRXscbd35Rz3t5e7HJRiDKthLO8VzvX61IaYPGBQNAg";

		final ResponseEntity<ValidationResponse> value = jwtUtils.validateJwtToken(token);
		final boolean result = value.getBody().getMessage().matches("JWT Token is Not Valid.*");

		assertTrue(result);

	}
}
