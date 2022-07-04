package com.tweetauthenticationservice.payload;

public class ValidationResponse {

	private Boolean isSuccess;
	private String userId;
	private String message;

	public ValidationResponse(String userId, String message, Boolean isSuccess) {
		super();
		this.userId = userId;
		this.message = message;
		this.isSuccess = isSuccess;
	}

	public ValidationResponse() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
