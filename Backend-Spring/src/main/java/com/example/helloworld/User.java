package com.example.helloworld;

public class User {
     String Name;
     int Accountnumber;
     int Balance;
     boolean Loanexist;
     
     User()
     {
    	 
     }
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getAccountnumber() {
		return Accountnumber;
	}

	public void setAccountnumber(int accountnumber) {
		Accountnumber = accountnumber;
	}

	public int getBalance() {
		return Balance;
	}

	public void setBalance(int balance) {
		Balance = balance;
	}

	public boolean isLoanexist() {
		return Loanexist;
	}

	public void setLoanexist(boolean loanexist) {
		Loanexist = loanexist;
	}
     
}
