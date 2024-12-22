package com.model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;

public class UserTransactionDetails {
	
	//transaction_id, balance_amount, transaction_amount, transaction_date, transaction_time, transaction_type, user_id
	private double balanceAmount;
	private double transactionAmount;
	private Date date ;
	private Time time;
	private String transactionType;
	private int userAccountNumber;
	private int transactionId;
	public UserTransactionDetails(double balanceAmount, double transactionAmount, Date date, Time time,
			String transactionType, int userAccountNumber) {
		super();
		this.balanceAmount = balanceAmount;
		this.transactionAmount = transactionAmount;
		this.date = date;
		this.time = time;
		this.transactionType = transactionType;
		this.userAccountNumber = userAccountNumber;
	}
	
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public UserTransactionDetails() {
		// TODO Auto-generated constructor stub
	}
	public double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public int getUserAccountNumber() {
		return userAccountNumber;
	}
	public void setUserAccountNumber(int userAccountNumber) {
		this.userAccountNumber = userAccountNumber;
	}
	@Override
	public String toString() {
		return "UserTransactionDetails [balanceAmount=" + balanceAmount + ", transactionAmount=" + transactionAmount
				+ ", date=" + date + ", time=" + time + ", transactionType=" + transactionType + ", userAccountNumber="
				+ userAccountNumber + "]";
	}
	
	
	
}
