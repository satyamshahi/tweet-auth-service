package com.tweetauthenticationservice.payload;

import javax.validation.constraints.NotNull;

public class ForgotPasswordRequest {

	@NotNull
	private String ques;
	@NotNull
	private String ans;
	@NotNull
	private String newPassword;

	public String getQues() {
		return ques;
	}

	public void setQues(String ques) {
		this.ques = ques;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
