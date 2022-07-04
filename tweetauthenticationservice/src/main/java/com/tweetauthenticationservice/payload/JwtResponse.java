package com.tweetauthenticationservice.payload;

import java.util.List;

/**
 * This class is used to store jwtResponse
 *
 */
public class JwtResponse {
	/**
	 * 
	 */
	private String token;
	/**
	 * 
	 */
	private String loginId;
	/**
	 * 
	 */
	private List<String> roles;

	public JwtResponse(String token, String loginId, List<String> roles) {
		super();
		this.token = token;
		this.loginId = loginId;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
