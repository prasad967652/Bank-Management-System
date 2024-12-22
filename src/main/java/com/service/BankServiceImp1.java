package com.service;

import java.util.List;
import java.util.Scanner;

import com.DAO.BankUserDAOImpl1;
import com.exceptions.BankUserException;
import com.DAO.BankUserDAO;
import com.model.BankUserDetails;


public class BankServiceImp1 implements BankService{
	Scanner sc=new Scanner(System.in);
	
	BankUserDAO bankUserDAO =new BankUserDAOImpl1();
	
	@Override
	public void forSleep(String value) {
		for(int i=0;i<value.length()-1;i++)
		{
			System.out.print(value.charAt(i));
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println();
	}

	@Override
	public void userRegistration() {
		
		BankUserDetails bankUserDetails=new BankUserDetails();
		//UserDetailsValidation userDetailsValidation=new UserDetailsValidation();
		
		List<BankUserDetails> allBankUserDetails=bankUserDAO.getAllBankUserDetails();
		
		System.out.println("Enter Your Name ");
		String name=sc.next();
		bankUserDetails.setName(name);
		
		
		System.out.println("Enter Your EmailId");
	boolean emailidstatus=true;
		while(emailidstatus) {
			try {
				
				  String emailid=sc.next();
		          if(emailid.endsWith("@gmail.com")) {
//		        	  int emailidcount=0;
//		        	  for(BankUserDetails bankUserDetails2:allBankUserDetails) {
//		        		  if(bankUserDetails2.getEmailid().equals(emailid))
//						  {
//		        			  emailidcount++;
//						  }
//					}
		           long emailidcount=allBankUserDetails.stream().filter((bankuser ->
		                              bankuser.getEmailid().equals(emailid)))
	        		              .count();
				if(emailidcount>0){
						throw new BankUserException("Already EmailId  Exist.");
					}
					else {
						emailidstatus=false;
					bankUserDetails.setEmailid(emailid);
					}
				 }
				else {
					throw new BankUserException("Inavalid  Email Id \n Please Re_Enter Mail That Should Ends With '@gmail.com'. ");
				}
				
			}
			catch(BankUserException e)
			{
				System.err.println(e.getMsg());
			}
		}
		
		
		System.out.println("Enter Your AdharNumber");
		boolean status=true;
		while(status) {
			try {
				
				long aadharnumber=sc.nextLong();
				long tempaadharnumber=aadharnumber;
				int count=0;
				while(tempaadharnumber>0)
				{
					count++;
					tempaadharnumber/=10;
				}
				if(count==12)
				{
//					int aadharnumbercount=0;
//					for(BankUserDetails bankUserDetails2:allBankUserDetails) {
//						if(bankUserDetails2.getAadharnumber()==aadharnumber)
//						{
//							aadharnumbercount++;
//						}
//					}
					long aadharcount=allBankUserDetails.stream().filter((bankuser -> 
                    bankuser.getAadharnumber()==aadharnumber))
		            .count();
					
					if(aadharcount>0){
						throw new BankUserException("Already Aadhar Number Exist.");
					}
					else {
						status=false;
						bankUserDetails.setAadharnumber(aadharnumber);
					}
				}
				else {
					throw new BankUserException("Inavalid  AadharNumber Please Enter 12 digits Aadhar Number ");
				}
				
			}
			catch(BankUserException e)
			{
				System.out.println(e.getMsg());
			}
		}
		
		
		System.out.println("Enter Your Mobile Number");
		boolean mobilenumberstatus=true;
		while(mobilenumberstatus) {
			try {
				
				long mobilenumber=sc.nextLong();
				long tempmobilenumber=mobilenumber;
				int count=0;
				while(tempmobilenumber>0)
				{
					count++;
					tempmobilenumber/=10;
				}
				if(count==10)
				{
//					int mobilenumbercount=0;
//					for(BankUserDetails bankUserDetails2:allBankUserDetails) {
//						if(bankUserDetails2.getMobilenumber()==mobilenumber)
//						{
//							mobilenumbercount++;
//						}
//					}
					long mobilenumbercount =allBankUserDetails.stream().filter((bankuser ->
					                  bankuser.getMobilenumber()==mobilenumber))
							          .count();
					if(mobilenumbercount>0){
						throw new BankUserException("Already Mobile Number Exist.");
					}
					else {
						mobilenumberstatus=false;
						bankUserDetails.setMobilenumber(mobilenumber);
					}
				}
				else {
					throw new BankUserException("Inavalid  Mobile Number Please Enter 10 digits Mobile Number ");
				}
				
			}
			catch(BankUserException e)
			{
				System.out.println(e.getMsg());
			}
		}
		
		
		System.out.println("Enter Your PAN Number");
		String pannumber=sc.next();
		bankUserDetails.setPannumber(pannumber);
		
		
		System.out.println("Enter Your Address");
		String address=sc.next();
		bankUserDetails.setAddress(address);
		
		
		System.out.println("Enter Your Gender");
		String gender=sc.next();
		bankUserDetails.setGender(gender);
		
		
		System.out.println("Enter Amount To Open An Account");
		double amount=sc.nextDouble();
		bankUserDetails.setAmount(amount);
		
		bankUserDAO.insertBankUserDetails(bankUserDetails);
	}
	
	
}
