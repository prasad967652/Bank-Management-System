package com;

import java.util.Scanner;


import com.DAO.BankUserDAOImpl1;
import com.service.AdminService;
import com.service.AdminServiceImpl;
import com.service.BankService;
import com.service.BankServiceImp1;
import com.service.*;
/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args ) 
    {
    	BankService service=new BankServiceImp1();
    	AdminService adminService = new AdminServiceImpl();
    	UserService userService= new UserServiceImpl();
    	service.forSleep("-------------------------------Welcome To Indian Bank----------------------");
    	
//    	String tittle="-------------------------------Welcome To Indian Bank----------------------";
//    	for(int i=0;i<=tittle.length()-1;i++)
//    	{
//    		Thread.sleep(70);
//    		System.out.print(tittle.charAt(i));
//    	}
        boolean istrue=true;
    	while(istrue)
    	{
    		
    		
            System.out.println("Enter Your Choice...... \n 1.For User Login \n 2.For Admin Login  \n 3.For User Registration  ")  ;
            Scanner sc=new Scanner(System.in);
            switch(sc.nextInt()){
            case 1:
            	service.forSleep("***--------User Login------------***");
            	userService.userLogin();
            	break;
            case 2:
            	service.forSleep("***-----------------Admin Login-----------------***");
            	adminService.adminLogin();
            	break;
            case 3:
            	service.forSleep("***-----------------User Registration-----------------***");
            	service.userRegistration();
            	
            	break;
            default :
            	System.out.println("Invalid Choice");
            	break;
            }
            System.out.println("Do You Want To Continue........");
            System.out.println("Yes or No");
    		String s=sc.next();
    		if(s.equalsIgnoreCase("No"))
    		{
    			service.forSleep("***---------Thank You For Visting Our Branch-----------***");
    			istrue=false;
    		}
    		
    		else if(s.equalsIgnoreCase("Yes"));
    		{
    			continue;
    		}
    	}
        
        
    }
}
