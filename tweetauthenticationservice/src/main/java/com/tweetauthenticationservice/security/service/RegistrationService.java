package com.tweetauthenticationservice.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tweetauthenticationservice.client.UpdateServiceClient;
import com.tweetauthenticationservice.payload.ForgotPasswordRequest;
import com.tweetauthenticationservice.payload.RegisterationRequest;

import feign.FeignException;

@Service
public class RegistrationService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	UpdateServiceClient updateServiceClient;

	public static final Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);

	public ResponseEntity<String> registrationUser(RegisterationRequest registerRequest) {

		registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("{}, Information: Calling update service to register new user ",
						this.getClass().getSimpleName());
			}
			return updateServiceClient.registerUser(registerRequest);
		} catch (FeignException.Conflict e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("{}, Information: FeignException.Conflict occurred while calling update service ",
						this.getClass().getSimpleName());
			}
			return new ResponseEntity<>(e.contentUTF8(), HttpStatus.valueOf(e.status()));
		}
	}

	public ResponseEntity<String> forgotPassword(ForgotPasswordRequest forgotPasswordRequest, String loginId) {

		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("{}, Information: Calling update service  ", this.getClass().getSimpleName());
			}
			forgotPasswordRequest.setNewPassword(passwordEncoder.encode(forgotPasswordRequest.getNewPassword()));
			return updateServiceClient.forgotPassword(forgotPasswordRequest, loginId);
		} catch (FeignException e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("{}, Information: FeignException occurred while calling update service ",
						this.getClass().getSimpleName());
			}
			return new ResponseEntity<>(e.contentUTF8(), HttpStatus.valueOf(e.status()));
		}
	}

}
