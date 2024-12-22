package com.exceptions;

public class AccountStatusException extends RuntimeException {
	String msg;

	public AccountStatusException(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
