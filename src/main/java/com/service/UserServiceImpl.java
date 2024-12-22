package com.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import com.DAO.UserDAO;
import com.DAO.UserDAOImpl;
import com.exceptions.BankUserException;
import com.exceptions.UserLoginException;
import com.exceptions.UserStatusException;
import com.model.BankUserDetails;
import com.model.UserTransactionDetails;

public class UserServiceImpl implements UserService {

	Scanner sc=new Scanner(System.in);
	Random random =new Random();
	@Override
	public void userLogin() {
		UserDAO userDAO=new UserDAOImpl();
		
		
		try {
			System.out.println("Enter Your Account Number");
			int accountnumber=sc.nextInt();
			System.out.println("Enter Your PIN");
			int pin =sc.nextInt();
			
			
			
			if(userDAO.getUserDetailsBooleanValueByUsingAccountNumberAndPin(accountnumber, pin) ) {
				boolean userStatus=true;
				BankUserDetails userDetails =
	                    userDAO.getUserDetailsByUsingAccountNumberAndPin(accountnumber, pin);
				while(userStatus)
				{	
					System.out.println("Enter Your Option ......");
					System.out.println("\n 1.Check Your Account Balance... \n 2.Deposite Amount  To Your Account \n 3.Withdrawn Amount From Your Account \n 4.Change Your Pin.. \n 5.Check Acount Statement....  \n 6.Transfer Funds ");
		
					switch(sc.nextInt())
					{ 
					case 1:
							if(userDetails.getAmount()<=100) {
								System.err.print("Your Account Balance Is Low :");
								System.err.println(userDetails.getAmount());
							}
							else {
								System.out.print("Your Account Balance Is:");
								System.out.println(userDetails.getAmount());
							}
						break;
					case 2:
						System.out.println("Enter Amount To Deposite In Your Bank");
						double depositeAmount=sc.nextDouble();
						int depositeResult=
								userDAO.updateDepositeAmountByUsingAccountNumberAndPin(accountnumber, pin, depositeAmount);
						if(depositeResult!=0) {
							userDAO.insertTransactionHistoryInDatabase(accountnumber, pin, depositeAmount, "Credited");
							System.out.println("Succefully Amount Deposited In Your Account");
							userDetails.setAmount(depositeAmount+userDetails.getAmount());
							System.out.println(userDetails.getAmount());
						}
						else {
							throw new UserStatusException("Deposite is Failure Due To Technical Issue");
						}
						break;
					case 3:
						System.out.println("Enter Amount You Want To Withdrawn");
						double withdrawnAmount=sc.nextDouble();
						if(withdrawnAmount<=userDetails.getAmount()) {
							int withdrawnResult =
								       userDAO.updateWithdrawnAmountByUsingAccountNumberAndPin(accountnumber, pin, withdrawnAmount);
						    if(withdrawnResult>=0)
						    {
						    	userDAO.insertTransactionHistoryInDatabase(accountnumber, pin, withdrawnAmount,"Debited");
						    	System.out.println("Succefully Amount of Rs "+withdrawnAmount+"  Withdrawn From Your Account");
						    	userDetails.setAmount(userDetails.getAmount()-withdrawnAmount);
						    	System.out.println("Your Current Balance Is:"+userDetails.getAmount());
							
						    }
						    else {
						    	throw new UserStatusException("Withdrawn is Failure Due To Server Issue");
						    }
						}
						else {
							throw new UserStatusException("InSufficent Founds In Your Account");
						}
						break;
					case 4:
						System.out.println("Enetr Old Pin");
						int oldPin=sc.nextInt();
						System.out.println("Enter Your New Pin");
						int newPin=sc.nextInt();
						System.out.println("Confirm Your New Pin");
						int newPinConfirm=sc.nextInt();
							if(userDetails.getPin()==oldPin) {
								if(newPin==newPinConfirm) {
									userDetails.setPin(newPinConfirm);
								int pinResult=
											userDAO.updatePinByUsingAccountNumber(accountnumber,newPin);
									System.out.println(pinResult);
									if(pinResult!=0)
									{
										System.out.println("Your Pin Has Been Changed Successfully");
									}
									else {
										throw new UserStatusException("Pin Not Update Due To Tecnical Glitch");
									}
								}
								else {
									throw new UserStatusException("New Pin And Confirm Pin Are Not Matched");
								}
							}
							else {
								throw new UserStatusException("You Enterd Incorrect Old Pin");
							}
					case 5:
						boolean isWant=true;
						List<UserTransactionDetails> userTransactions=userDAO.getAllTransactionOfUser(accountnumber);
						while(isWant) {
							System.out.println("***********************Your Account Statements  **********************************");
							System.out.println("Enter Which Option You Want.....");
							System.out.println(" \n 1.Total Statements \n 2. Credited Statements \n 3.Debited Statements ");
							switch(sc.nextInt())
							{
							case 1: 
								System.out.println("Your Total  Statements  .........");
				
								userTransactions.forEach((transaction)->{
									System.out.println(transaction);
								});
								break;
							case 2:
								System.out.println("Your Credited  Statements .........");
								userTransactions.forEach((transaction)->{
									if(transaction.getTransactionType().equalsIgnoreCase("Credited")) {
										System.out.println(transaction);
									}
								});
								break;
							case 3:
								System.out.println("Your Debited  Statements .........");
								userTransactions.forEach((transaction)->{
									if(transaction.getTransactionType().equalsIgnoreCase("Debited")) {
										System.out.println(transaction);
									}
								});
								break;
							}
							System.out.println("Dou You Want To Continue");
							System.out.println("Enter  ******* 1.Continue ******* 2.Exit******* ");
							int choice=sc.nextInt();
							if(choice!=1)
							{
								isWant=false;
							}
						}
						break;
					case 6:
						try {
							System.out.println("Enter Amount To Transfer To Another Account");
							double transferAmount=sc.nextInt();
							if(userDetails.getAmount()>transferAmount) {
								System.out.println("Enter Reciever's Account Number ");
								int recieverAccountNumber=sc.nextInt();
								if(userDAO.getUserDetailsBooleanValueByUsingAccountNumber(recieverAccountNumber)) {
									int recieverPin=userDAO.getPinByUsingAccountNumber(recieverAccountNumber);
									int fundTransferSend =
											userDAO.updateWithdrawnAmountByUsingAccountNumberAndPin(accountnumber,pin,transferAmount );
									userDAO.insertTransactionHistoryInDatabase(accountnumber, pin,transferAmount ,"Debited");
									int fundTransferRecive=
											userDAO.updateDepositeAmountByUsingAccountNumberAndPin(recieverAccountNumber, recieverPin, transferAmount);
									userDAO.insertTransactionHistoryInDatabase(recieverAccountNumber, recieverPin, transferAmount, "Credited");
									
									System.out.println("Funds Transfered Successfully...... Have A Nice Day.......");
									
								}
								else {
									throw new UserStatusException("Reciever Account Can't Find In DataBase");
								}
						
							}
							else {
								throw new UserStatusException("You Have Insufficient Funds In Your Exception");
							}
							
						}
						catch(UserStatusException e) {
							System.out.println(e.getMsg());
						}
					}
				}
	        }
		    else {
				throw new UserLoginException("Invalid User Login Details");
			}
		
	    }
		catch(UserLoginException e) {
			System.out.println(e.getMsg());
		}
		catch(UserStatusException e) {
			System.out.println(e.getMsg());
		}
	
	}
	
	
	
}
