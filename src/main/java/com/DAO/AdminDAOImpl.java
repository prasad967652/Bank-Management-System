package com.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements AdminDAO{
	private static final String url="jdbc:mysql://localhost:3306/teca_63?user=root&password=12345";
	private static final String select="select * from admin where admin_emailid=? and admin_password=?";
	private static final String update="update bank_user_details set pin=?,account_number=?,status=? where aadhar_number=? ";
	@Override
	public boolean getAdminDetailsByUsingEmailidAndPassword(String emailid,String password) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(select);
			preparedStatement.setString(1,emailid);
			preparedStatement.setString(2, password);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
			{
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
		
		
		
	}
	@Override
	public int updatePinAndAccountNumberAndStatusByUsingAadharNumber(int pin, int accountnumber, long aadharnumber) {
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(update);
			preparedStatement.setInt(1,pin);
			preparedStatement.setInt(2, accountnumber);
			preparedStatement.setString(3,"Accepted");
			preparedStatement.setLong(4, aadharnumber);
			return preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			return 0;
		}
		
		
	}
	
}
