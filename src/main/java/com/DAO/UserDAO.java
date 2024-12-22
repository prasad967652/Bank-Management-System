package com.DAO;

import java.util.List;

import com.model.BankUserDetails;
import com.model.UserTransactionDetails;

public interface UserDAO {
	boolean getUserDetailsBooleanValueByUsingAccountNumberAndPin(int accountnumber,int pin);
	BankUserDetails getUserDetailsByUsingAccountNumberAndPin(int accountnumber,int pin);
	int updateDepositeAmountByUsingAccountNumberAndPin(int accountnumber, int pin,double amount);
	int updateWithdrawnAmountByUsingAccountNumberAndPin(int accountnumber, int pin,double amount);
	int updatePinByUsingAccountNumber(int accountnumber,int newPin);
	void insertTransactionHistoryInDatabase(int accountnumber,int pin,double amount,String type);
	List<UserTransactionDetails> getAllTransactionOfUser(int accountnumber);
	boolean getUserDetailsBooleanValueByUsingAccountNumber(int accountnumber);
	int getPinByUsingAccountNumber(int accountnumber);
}
