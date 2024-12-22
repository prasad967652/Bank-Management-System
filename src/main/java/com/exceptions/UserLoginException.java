package com.exceptions;

public class UserLoginException extends RuntimeException{
	String msg;

	public UserLoginException(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
