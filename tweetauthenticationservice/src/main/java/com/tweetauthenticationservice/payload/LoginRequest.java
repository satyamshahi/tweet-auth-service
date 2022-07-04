package com.tweetauthenticationservice.payload;

import javax.validation.constraints.Pattern;

/**
 * This class is used to store the LogineRequest
 *
 */
public class LoginRequest {

	@Pattern(regexp = "^[a-zA-Z0-9].*")
	private String loginId;

	@Pattern(regexp = "^[a-zA-Z0-9].*")
	private String password;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
