package com.util;

import com.bean.UserInformation;

public class MessageBean {
	int statusCode;
	String statusMessage;
	String messageCode;
	UserInformation userinfo;
	
	public UserInformation getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(UserInformation userinfo) {
		this.userinfo = userinfo;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
}
