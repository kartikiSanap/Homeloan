package com.example.helloworld;

public class Repayment_schedule {
	int id;
	String month_year;
	int loan_id;
	float emi;
	float principal_component;
	float interest_component;
	float principal_outstanding;
	String payment_status;
	public int getId() {
	return id;
	}
	public void setId(int id) {
	this.id = id;
	}
	public String getMonth_year() {
	return month_year;
	}
	public void setMonth_year(String month_year) {
	this.month_year = month_year;
	}
	public int getLoan_id() {
	return loan_id;
	}
	public void setLoan_id(int loan_id) {
	this.loan_id = loan_id;
	}
	public float getEMI() {
	return emi;
	}
	public void setEMI(float eMI) {
	emi = eMI;
	}
	public float getPrincipal_component() {
	return principal_component;
	}
	public void setPrincipal_component(float principal_component) {
	this.principal_component = principal_component;
	}
	public float getInterest_component() {
	return interest_component;
	}
	public void setInterest_component(float interest_component) {
	this.interest_component = interest_component;
	}
	public float getPrincipal_outstanding() {
	return principal_outstanding;
	}
	public void setPrincipal_outstanding(float principal_outstanding) {
	this.principal_outstanding = principal_outstanding;
	}
	public String getPayment_status() {
	return payment_status;
	}
	public void setPayment_status(String payment_status) {
	this.payment_status = payment_status;
	}
}
