package com.DAO;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.BankUserDetails;

public class BankUserDAOImpl1 implements BankUserDAO{
	private static final String url="jdbc:mysql://localhost:3306/teca_63?user=root&password=12345";
	
	private static final String insert="insert into bank_user_details"
			+ "(name, email_id, aadhar_number, mobile_number, pan_number, address, gender, amount, status)"
			+ " values(?,?,?,?,?,?,?,?,?)";
	private static final String select_all ="select * from bank_user_details";
	//BankUserDetails bankUserDetails=new BankUserDetails();
	@Override
	public void insertBankUserDetails(BankUserDetails bankUserDetails)  {
		
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setString(1,bankUserDetails.getName());
			preparedStatement.setString(2,bankUserDetails.getEmailid());
			preparedStatement.setLong(3,bankUserDetails.getAadharnumber());
			preparedStatement.setLong(4,bankUserDetails.getMobilenumber());
			preparedStatement.setString(5,bankUserDetails.getPannumber());
			preparedStatement.setString(6,bankUserDetails.getAddress());
			preparedStatement.setString(7,bankUserDetails.getGender());
			preparedStatement.setDouble(8,bankUserDetails.getAmount());
			preparedStatement.setString(9,"pending");
			
			int result=preparedStatement.executeUpdate();
			//System.out.println(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	@Override
	public List<BankUserDetails> getAllBankUserDetails() {
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(select_all);
			ResultSet resultSet=preparedStatement.executeQuery();
			ArrayList<BankUserDetails> list=new  ArrayList<BankUserDetails>();
			if(resultSet.isBeforeFirst()) {
				while(resultSet.next()) {
					BankUserDetails bankUserDetails=new BankUserDetails();
					bankUserDetails.setName(resultSet.getString("name"));
					bankUserDetails.setAccountnumber(resultSet.getInt("account_number"));
					bankUserDetails.setEmailid(resultSet.getString("email_id"));
					bankUserDetails.setAadharnumber(resultSet.getLong("aadhar_number"));
					bankUserDetails.setMobilenumber(resultSet.getLong("mobile_number"));
					bankUserDetails.setPannumber(resultSet.getString("pan_number"));
					bankUserDetails.setAddress(resultSet.getString("address"));
					bankUserDetails.setGender(resultSet.getString("gender"));
					bankUserDetails.setAmount(resultSet.getDouble("amount"));
					bankUserDetails.setStatus(resultSet.getString("status"));     
					//bankUserDetails.setEmailid(resultSet.getString("email_id"));
					
					
					
					list.add(bankUserDetails);
				}
				return list;
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
}

