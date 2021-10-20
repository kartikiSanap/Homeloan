package com.example.helloworld;

public class Savings_account {
	int account_no;
	String name;
	float balance;
	String email;
	boolean loan_exists;
	public int getAccount_no() {
	return account_no;
	}
	public void setAccount_no(int account_no) {
	this.account_no = account_no;
	}
	public String getName() {
	return name;
	}
	public void setName(String name) {
	this.name = name;
	}
	public float getBalance() {
	return balance;
	}
	public void setBalance(float balance) {
	this.balance = balance;
	}
	public String getEmail() {
	return email;
	}
	public void setEmail(String email) {
	this.email = email;
	}
	public boolean isLoan_exists() {
	return loan_exists;
	}
	public void setLoan_exists(boolean loan_exists) {
	this.loan_exists = loan_exists;
	}

}
