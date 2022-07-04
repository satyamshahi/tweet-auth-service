package com.tweetauthenticationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tweetauthenticationservice.payload.ForgotPasswordRequest;
import com.tweetauthenticationservice.payload.LoginResponse;
import com.tweetauthenticationservice.payload.RegisterationRequest;

@FeignClient(value = "tweet-authentication-service", url = "http://15.207.99.206:6200")
public interface UpdateServiceClient {

	@GetMapping("{loginId}/login")
	public LoginResponse login(@PathVariable String loginId);

	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerUser(@RequestBody RegisterationRequest user);

	@PostMapping("/{loginId}/forgot")
	public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest,
			@PathVariable String loginId);

}
