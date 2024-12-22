package com.DAO;

import java.util.List;


import com.model.BankUserDetails;

public interface BankUserDAO {
	void insertBankUserDetails(BankUserDetails bankUserDeatils);
	
	List<BankUserDetails> getAllBankUserDetails();
	
}
