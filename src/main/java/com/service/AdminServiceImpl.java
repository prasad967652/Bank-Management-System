package com.service;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.DAO.AdminDAO;
import com.DAO.AdminDAOImpl;
import com.DAO.BankUserDAO;
import com.DAO.BankUserDAOImpl1;
import com.exceptions.AccountStatusException;
import com.exceptions.AdminLoginException;
import com.model.BankUserDetails;

public class AdminServiceImpl implements AdminService {

	Scanner sc=new Scanner(System.in);
	AdminDAO adminDAO = new AdminDAOImpl();
	BankUserDAO bankuserdao=new BankUserDAOImpl1();
	@Override
	public void adminLogin() {
		try {
			System.err.println("Enter Admin Email Id");
			String emailid=sc.next();
			System.err.println("Enter Admin Password");
			String password=sc.next();
			if(adminDAO.getAdminDetailsByUsingEmailidAndPassword(emailid, password))
			{
				System.out.println(" 1.To Get All User Details \n 2.To get All Account Request Details");
				int choice=sc.nextInt();
				boolean status=true;
				switch(choice)
				{
				case 1:
					System.err.println("All User Details");
					getAllAccountAcceptedUserDetails();
					break;
				case 2:
					System.out.println("All Account Pending Requests");
					getAllAccountRequestDetails();
					break;
				}
			}
			else {
				throw new AdminLoginException("Inavlid Admin Credentials Are Entered... ");
			}
		}
		catch(AdminLoginException e) {
			System.out.println(e.getMsg());
		}
	}
	
	@Override
	public void getAllAccountRequestDetails()
	{
	   
		BankUserDAO bankUserDAO=new BankUserDAOImpl1();
		List<BankUserDetails> allBankUserDetails =bankUserDAO.getAllBankUserDetails();
	
		List<BankUserDetails> list =
				allBankUserDetails.stream().filter((user -> user.getStatus().equalsIgnoreCase("pending"))).collect(Collectors.toList());
	
		int[] count= {0};
		list.forEach( (user)->{
			count[0]++;
			
			System.out.println("S.no :"+count[0]);
			System.out.println("User Name :"+user.getName());
			System.out.println("User Emai Id :"+user.getEmailid());
			System.out.println("User Mobile Number :"+user.getMobilenumber());
			System.out.println("User Aadhar Number :"+user.getAadharnumber());
			System.out.println("User Pan Number :"+user.getPannumber());
			System.out.println("User Address :"+user.getAddress());
			System.out.println("User Status :"+user.getStatus());
		});
		
		try {
			if(count[0]>0)
			{
				System.err.println("Enter S.No To Select  User");
				BankUserDetails bankUserDetail=list.get(sc.nextInt()-1);
				System.err.println(bankUserDetail);
//				int snoCount=0;
//				long selectUser=sc.nextLong();	
//				list.forEach((user)->{
//					snoCount++;
//					if(selectUser==snoCount) {
//						System.out.println(user.toString());
//					}
//				});
				
				System.out.println("Enter \n 1.Accept Request \n 2.Reject Request");
				switch(sc.nextInt())
				{
				case 1:
					System.out.println("Accepted");
					acceptRequest(bankUserDetail.getAadharnumber());
					continueAccountRequestDetails();
					break;
				case 2:
					System.err.println("Rejected");
					break;
				default:
					break;
			
		       }
		    }
			else {
				throw new AccountStatusException("No Pending AccountRequests");
			}
		}
		catch(AccountStatusException e)
		{
			System.err.println(e.getMsg());
		}
	}
	
	@Override
	public void acceptRequest(long aadharnumber) {
		// TODO Auto-generated method stub
		Random random=new Random();
		int pin=random.nextInt(10000);
		if(pin<1000)
		{
			pin=pin+1000;
		}
		int accountnumber=random.nextInt(10000000);
		if(accountnumber<1000000) {
			accountnumber+=1000000;
		}
		int result=adminDAO.updatePinAndAccountNumberAndStatusByUsingAadharNumber(pin, accountnumber, aadharnumber);
		if(result!=0)
		{
			System.err.println("Your Account Opened SuccessFulyy.......");
			System.out.println("Your Account Number Is:"+accountnumber);
			System.out.println("Your Pin Is:"+pin);
		}
		else {
			throw new AccountStatusException("Server 404 Issue Account Not Accepted");
		}
	}
	

	@Override
	public void continueAccountRequestDetails() {
		System.out.println("Do You Want To Continue To Get Account Request Details \n \n");
		System.err.println("Enter  A Number  1.Continue...... OR  2.Back.........");
			if(sc.nextInt()==1)
			{
				BankUserDAO bankUserDAO=new BankUserDAOImpl1();
				List<BankUserDetails> allBankUserDetails =bankUserDAO.getAllBankUserDetails();
			
				List<BankUserDetails> list =
						allBankUserDetails.stream().filter((user -> user.getStatus().equalsIgnoreCase("pending"))).collect(Collectors.toList());
			
				int[] count= {0};
				list.forEach( (user)->{
					count[0]++;
				});
				if(count[0]>0)
				{
					getAllAccountRequestDetails();
				}
				else {
					try {
						throw new AccountStatusException("No Pending Accounts");
					}
					catch(AccountStatusException e)
					{
						System.err.println(e.getMsg());
					}
				}
			}
			else {
				adminLogin();
			}
	}

	@Override
	public void getAllAccountAcceptedUserDetails() {
		BankUserDAO bankUserDAO=new BankUserDAOImpl1();
		List<BankUserDetails> allBankUserDetails =bankUserDAO.getAllBankUserDetails();
	
		List<BankUserDetails> list =
				allBankUserDetails.stream().filter((user -> user.getStatus().equalsIgnoreCase("Accepted"))).collect(Collectors.toList());
		int[] count= {0};
		list.forEach( (user)->{
			count[0]++;
			
			System.out.println(" \n \n USER   "+ count[0]+"  DETAILS \n \n  ");
			
			System.out.println("S.no :"+count[0]);
			System.out.println("User Name :"+user.getName());
			System.out.println("User Account Number :"+user.getAccountnumber());
			System.out.println("User Emai Id :"+user.getEmailid());
			System.out.println("User Mobile Number :"+user.getMobilenumber());
			System.out.println("User Aadhar Number :"+user.getAadharnumber());
			System.out.println("User Pan Number :"+user.getPannumber());
			System.out.println("User Address :"+user.getAddress());
			System.out.println("User Gender :"+user.getGender());
			System.out.println("User Balance :"+user.getAmount());
			
		});
	}
	
}
