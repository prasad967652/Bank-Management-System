package com.DAO;

public interface AdminDAO {
	
	boolean getAdminDetailsByUsingEmailidAndPassword(String emailid,String password);
	int updatePinAndAccountNumberAndStatusByUsingAadharNumber(int pin,int accountnumber,long aadharnumber);
}
