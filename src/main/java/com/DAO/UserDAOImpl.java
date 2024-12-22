package com.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.model.BankUserDetails;
import com.model.UserTransactionDetails;

public class UserDAOImpl implements UserDAO {

	private static final String url="jdbc:mysql://localhost:3306/teca_63?user=root&password=12345";
	private static final String select="select * from bank_user_details where account_number=? and pin=?";
	private static final String updateDepositeAmount="update bank_user_details set amount=? where account_number=? and pin=?";
	private static final String updateWithdrawnAmount="update bank_user_details set amount=? where account_number=? and pin=?";
	private static final String updatePin="update bank_user_details set pin=? where account_number=?";
	private static final String getPin="select pin from bank_user_details where account_number=?";
	private static final String insertTransaction="insert into transaction_table"
			+ "( balance_amount, transaction_amount, transaction_date, transaction_time, transaction_type, user_id)  "
			+ "values(?,?,?,?,?,?)";
	private static final String selectAll="select * from transaction_table where user_id=?";
	private static final String selectReciever="select * from bank_user_details where account_number=? ";
	private static final String selectPin="select pin from bank_user_details where account_number=?";
	
	@Override
	public BankUserDetails getUserDetailsByUsingAccountNumberAndPin(int accountnumber, int pin) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(select);
			preparedStatement.setInt(1,accountnumber);
			preparedStatement.setInt(2, pin);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
			{
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
				bankUserDetails.setPin(resultSet.getInt("pin"));
				return bankUserDetails;
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			return null;
		}
		
	}
	@Override
	public boolean getUserDetailsBooleanValueByUsingAccountNumberAndPin(int accountnumber, int pin) {
		
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(select);
			preparedStatement.setInt(1,accountnumber);
			preparedStatement.setInt(2, pin);
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
	public int updateDepositeAmountByUsingAccountNumberAndPin(int accountnumber, int pin,double amount) {
		
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(updateDepositeAmount);
			BankUserDetails userLoginDetails=getUserDetailsByUsingAccountNumberAndPin(accountnumber, pin);
			double oldAmount=userLoginDetails.getAmount();
			preparedStatement.setDouble(1,amount+oldAmount);
			preparedStatement.setInt(2, accountnumber);
			preparedStatement.setInt(3,pin);
			return preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			return 0;
		}
	}
	@Override
	public int updateWithdrawnAmountByUsingAccountNumberAndPin(int accountnumber, int pin, double amount) {
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(updateWithdrawnAmount);
			BankUserDetails userLoginDetails=getUserDetailsByUsingAccountNumberAndPin(accountnumber, pin);
				double oldAmount=userLoginDetails.getAmount();
			preparedStatement.setDouble(1,oldAmount-amount);
			preparedStatement.setInt(2, accountnumber);
			preparedStatement.setInt(3,pin);
			return preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			return 0;
		}	
	}
	@Override
	public int updatePinByUsingAccountNumber(int accountnumber,int newPin) {
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(updatePin);
			//List<BankUserDetails> list=getUserDetailsByUsingAccountNumberAndPin(accountnumber, password   );
			preparedStatement.setInt(1,newPin);
			preparedStatement.setInt(2, accountnumber);
			return preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			return 0;
		}	
	}
	@Override
	public void insertTransactionHistoryInDatabase(int accountnumber,int pin,double amount,String type) {
		BankUserDetails userLoginDetails=getUserDetailsByUsingAccountNumberAndPin(accountnumber,pin);
		UserTransactionDetails userTransactions=new UserTransactionDetails();
		userTransactions.setBalanceAmount(userLoginDetails.getAmount());
		userTransactions.setTransactionAmount(amount);
		userTransactions.setDate(Date.valueOf(LocalDate.now()));
		userTransactions.setTime(Time.valueOf(LocalTime.now()));
		userTransactions.setTransactionType(type);
		userTransactions.setUserAccountNumber(accountnumber);
		
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(insertTransaction);
			preparedStatement.setDouble(1,userTransactions.getBalanceAmount());
			preparedStatement.setDouble(2,userTransactions.getTransactionAmount());
			preparedStatement.setDate(3,userTransactions.getDate());
			preparedStatement.setTime(4,userTransactions.getTime());
			preparedStatement.setString(5,userTransactions.getTransactionType());
			preparedStatement.setInt(6,userTransactions.getUserAccountNumber());	
			int result=preparedStatement.executeUpdate();
			//System.out.println(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Override
	public List<UserTransactionDetails> getAllTransactionOfUser(int accountnumber) {
		try {
			ArrayList<UserTransactionDetails> list=new ArrayList<UserTransactionDetails>();
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(selectAll);
			preparedStatement.setInt(1,accountnumber);
			ResultSet resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				UserTransactionDetails userTransaction=new UserTransactionDetails();
				userTransaction.setTransactionId(resultSet.getInt("transaction_id"));
				userTransaction.setUserAccountNumber(resultSet.getInt("user_id"));
				userTransaction.setBalanceAmount(resultSet.getDouble("balance_amount"));
				userTransaction.setTransactionAmount(resultSet.getDouble("transaction_amount"));
				userTransaction.setDate(resultSet.getDate("transaction_date"));
				userTransaction.setTime(resultSet.getTime("transaction_time"));
				userTransaction.setTransactionType(resultSet.getString("transaction_type"));
				
				list.add(userTransaction);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public boolean getUserDetailsBooleanValueByUsingAccountNumber(int accountnumber) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(selectReciever);
			preparedStatement.setInt(1,accountnumber);
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
	public int getPinByUsingAccountNumber(int accountnumber) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(selectReciever);
			preparedStatement.setInt(1,accountnumber);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
			{
				return resultSet.getInt("pin");
			}
			else {
				return 0;
			}
		} catch (SQLException e) {
			return 0;
		}
	}

}
