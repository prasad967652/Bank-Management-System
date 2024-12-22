package com.exceptions;

public class UserStatusException extends RuntimeException{
	String msg;

	public UserStatusException(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
