package com.service;

public interface AdminService {
	void adminLogin();
	
	void getAllAccountRequestDetails();
	
	void acceptRequest(long aadharnumber);
	
	void continueAccountRequestDetails();
	
	void getAllAccountAcceptedUserDetails();
}
