package com.tweetauthenticationservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetauthenticationservice.payload.ForgotPasswordRequest;
import com.tweetauthenticationservice.payload.JwtResponse;
import com.tweetauthenticationservice.payload.LoginRequest;
import com.tweetauthenticationservice.payload.RegisterationRequest;
import com.tweetauthenticationservice.payload.ValidationResponse;
import com.tweetauthenticationservice.security.jwt.JwtUtils;
import com.tweetauthenticationservice.security.service.RegistrationService;

/**
 * This is authentication controller class
 *
 */
@RestController
@RequestMapping("/api/v1.0/tweets")
@CrossOrigin(origins="http://localhost:4200")
public class AuthController {

	private final AuthenticationManager authManager;

	private final JwtUtils jwtUtils;

	public static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	RegistrationService registrationService;

	public AuthController(final AuthenticationManager authManager, final JwtUtils jwtUtils) {
		this.authManager = authManager;
		this.jwtUtils = jwtUtils;
	}

	/**
	 * This Rest Point is used for UserCredential Signing and generate Jwt token.
	 */
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody final LoginRequest loginRequest,
			final BindingResult result) throws RuntimeException {

		if (result.hasErrors()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(
						"{}, Information: BindingResult {} has errors - Blank username and/or password BAD REQUEST ",
						this.getClass().getSimpleName(), result);
			}
			return new ResponseEntity<>("Blank username and/or password!", HttpStatus.BAD_REQUEST);
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("{}, Information: LoginRequest - {} ", this.getClass().getSimpleName(), loginRequest);
			}

			final Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword()));

			final SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
			final User userPrincipal = (User) authentication.getPrincipal();
			final String jwt = jwtUtils.generateJwtToken(userPrincipal.getUsername());

			final User userDetails = (User) authentication.getPrincipal();
			final List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());

			return new ResponseEntity<>(new JwtResponse(jwt, userDetails.getUsername(), roles), HttpStatus.OK);
		}

	}

	/**
	 * This Rest Point is used for Validate the user, using Jwt token.
	 */
	@PostMapping("/validate")
	public ResponseEntity<ValidationResponse> validateAndReturnUser(
			@RequestHeader("Authorization") final String token) {
		final String jwttoken = token.substring(7);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}, Information: Validating user using JWT token ", this.getClass().getSimpleName());
		}

		return jwtUtils.validateJwtToken(jwttoken);

	}

	/**
	 * This Rest Point is used for register new user.
	 */
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody final RegisterationRequest registerRequest,
			final BindingResult result) {

		if (result.hasErrors()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(
						"{}, Information: BindingResult {} has errors - One or more fields are blank. Register request -{} BAD REQUEST ",
						this.getClass().getSimpleName(), result, registerRequest);
			}
			return new ResponseEntity<>("One or more fields are blank", HttpStatus.BAD_REQUEST);
		}
		return registrationService.registrationUser(registerRequest);

	}

	/**
	 * This Rest Point is used for forgot Password.
	 */
	@GetMapping("/{username}/forgot")
	public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest,
			@PathVariable String username, final BindingResult result) {
		if (result.hasErrors()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(
						"{}, Information: BindingResult {} has errors - Blank username and/or password BAD REQUEST ",
						this.getClass().getSimpleName(), result);
			}
			return new ResponseEntity<>("One or more field Blank", HttpStatus.BAD_REQUEST);
		}
		return registrationService.forgotPassword(forgotPasswordRequest, username);
	}

}