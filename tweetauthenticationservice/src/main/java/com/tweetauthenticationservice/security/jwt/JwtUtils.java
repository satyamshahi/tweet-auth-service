package com.tweetauthenticationservice.security.jwt;

import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tweetauthenticationservice.payload.ValidationResponse;
import com.tweetauthenticationservice.security.service.JwtSignature;

import java.util.Date;

/**
 * This is JwtUtils class
 *
 */
@Component
public class JwtUtils {

	@Autowired
	private JwtSignature jwtSignature;

	public static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

	/**
	 * This method generated the jwt token using userName, secret Key and jwt
	 * Expiration time.
	 */
	public String generateJwtToken(final String userName) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}, Information: Generating JWT token for user - {} ", this.getClass().getSimpleName(),
					userName);
		}

		final int jwtExpiration = jwtSignature.getJwtExpirationMs();

		final String secret = jwtSignature.getJwtSecret();

		return Jwts.builder().setSubject(userName).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	/**
	 * This method is used to get userName from Jwt token.
	 */
	public String getUserNameFromJwtToken(final String token) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}, Information: Generating user name from JWT token ", this.getClass().getSimpleName());
		}
		final String jwtSecret = jwtSignature.getJwtSecret();
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * This method is used validation of user Jwt token.
	 */
	public ResponseEntity<ValidationResponse> validateJwtToken(final String authToken) {
		ValidationResponse validationResponse = new ValidationResponse();
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("{}, Information: Validating JWT token ", this.getClass().getSimpleName());
			}
			final String jwtSecret = jwtSignature.getJwtSecret();
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);

			validationResponse.setIsSuccess(true);
			validationResponse.setMessage("Validated Successfully....");
			validationResponse.setUserId(getUserNameFromJwtToken(authToken));

			return new ResponseEntity<>(validationResponse, HttpStatus.OK);

		} catch (Exception e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("{}, Information: Exception occurred while validating JWT token ",
						this.getClass().getSimpleName());
			}
			validationResponse.setIsSuccess(false);
			validationResponse.setMessage("JWT Token is Not Valid");
			validationResponse.setUserId("");
			return new ResponseEntity<>(validationResponse, HttpStatus.UNAUTHORIZED);
		}

	}
}
