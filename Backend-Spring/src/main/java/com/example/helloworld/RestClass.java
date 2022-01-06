package com.example.helloworld;
import java.util.ArrayList;



import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.sql.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
@CrossOrigin(origins="http://localhost:4200")
@RestController
public class RestClass {   
@Autowired
DataSource datasource;


@RequestMapping(value="Home/Login",method=RequestMethod.POST,produces= {"application/json"})
public Map<String,Object> fun(@RequestBody Map<String, Object> payload) throws Exception
{
//this function returns an object. 
String user = (String) payload.get("username");
String pass = (String) payload.get("password");
Map<String,Object> m = new HashMap<>();
System.out.println("user");
String status="";
String res="";
String sql = "select password from users where username like '%"+user+"%';";
//Login login = new Login();
System.out.println(sql);
try {
Connection con = datasource.getConnection();
PreparedStatement ps = con.prepareStatement(sql);
ResultSet rst = ps.executeQuery();
if(rst.next()) {
           res+=rst.getString(1);
           if(res.equals(pass))
           {
    System.out.println("pass");
    //login.setResult("success");
    m.put("result","success");

        

           }
           else
           {
        	   //login.setResult("fail");
               m.put("result","failure");
        	 
        	   
           }
           
        }

con.close();
con = null;
//return login;
return m;
}

catch(Exception e)
{
throw new Exception(e);
}

}

@PostMapping("Home/Loan")//get loan table entries using account
public  Loan_accounts getLoan(@RequestBody Map<String, Object> payload) throws Exception
{
String user = (String) payload.get("username");
// String acc = (String) payload.get("account_no");

Loan_accounts ls = new Loan_accounts();
String sql = "select * from savings_accounts where email like '%"+user+"%'";
int bal =0;
System.out.println(sql);
try
{
Connection con=datasource.getConnection();

PreparedStatement ps=con.prepareStatement(sql);
ResultSet rs=ps.executeQuery();
int acc = 0;
int l = 0;
if(rs.next()) {
 acc = rs.getInt("account_no");
}
con.close();

String res="";
sql = "select * from loan_accounts where account_no ="+acc+";";
System.out.println(sql);

con = datasource.getConnection();
ps = con.prepareStatement(sql);
ResultSet rst = ps.executeQuery();
while(rst.next()) {
ls.setLoan_id(rst.getInt("loan_id"));;
ls.setTenure(rst.getInt("tenure"));
ls.setAccount_no(rst.getInt("account_no"));
ls.setLoan_status(rst.getString("loan_status"));
ls.setTotal_loan(rst.getFloat("total_loan"));

ls.setInterest_rate(rst.getFloat("interest_rate"));;
//LongBlob
ls.setProperty_address(rst.getString("property_address"));;


           res+=" "+rst.getString(1);
           res+=" "+rst.getString(2);
           res+=" "+rst.getString(3);
           res+=" "+rst.getString(4);
           
    System.out.println(res);
   

           
         
        }

con.close();
con = null;
return ls;
}
catch(Exception e)
{
throw new Exception(e);
}
}
@PostMapping("Home/Saving")//get loan table entries using account
public Savings_account  getSavings(@RequestBody Map<String, Object> temp) throws Exception
{
String username = (String) temp.get("username");
System.out.println("username");
String sql = "select *from Savings_accounts where email like '%"+username+"%'";
Savings_account sa = new Savings_account();
System.out.println(sql);
try
{
Connection conn=datasource.getConnection();

PreparedStatement ps=conn.prepareStatement(sql);
ResultSet rs=ps.executeQuery();
String res="";
while(rs.next()) {
sa.setAccount_no(rs.getInt("account_no"));
sa.setName(rs.getString("name"));
sa.setBalance(rs.getFloat("balance"));
sa.setEmail(rs.getString("email"));
sa.setLoan_exists(rs.getBoolean("loan_exists") );
res+=" "+rs.getString(1);
        res+=" "+rs.getString(2);
        res+=" "+rs.getString(3);
        res+=" "+rs.getString(4);
        res+=" "+rs.getString(5);

       }
System.out.println(res);
conn.close();
    conn = null;
return sa;
     
}

catch(Exception e)
{
throw new Exception(e);
}
}

@PostMapping("Home/Loan/Payment")//Make emi payment using username success = 1
public List<Repayment_schedule> PaymentEmi(@RequestBody Map<String,Object> temp ) throws Exception
{
//Repayment_schedule rs1 = new Repayment_schedule();
List<Repayment_schedule> list= new ArrayList<>();
String username = (String) temp.get("username");
String sql = "select * from savings_accounts where email like '%"+username+"%'";
int bal =0;
System.out.println(sql);
try
{
Connection conn=datasource.getConnection();

PreparedStatement ps=conn.prepareStatement(sql);
ResultSet rs=ps.executeQuery();
int acc = 0;
int l = 0;
if(rs.next()) {
bal =  rs.getInt("balance");
 acc = rs.getInt("account_no");
sql = "select loan_id from loan_accounts where account_no = "+acc+"";
System.out.println(sql);
conn.close();
conn=datasource.getConnection();
ps=conn.prepareStatement(sql);
rs=ps.executeQuery();
if(rs.next()) {

 l = rs.getInt("loan_id");
System.out.println(l+" loan id");

}

sql = "select * from repayment_schedule where loan_id = "+l+" and payment_status ='pending'  order by id limit 1";
System.out.println(sql);
conn.close();
conn=datasource.getConnection();
ps=conn.prepareStatement(sql);
rs=ps.executeQuery();
int emi = 0;
int id = 0;
if(rs.next()) {
id = rs.getInt("id");
 emi = rs.getInt("emi");
System.out.println(emi+" lemi");

 sql = "update repayment_schedule set payment_status ='paid'  where id ="+id+";";
 String sql1 = "update savings_accounts set balance ="+(bal-emi)+" where account_no="+acc+" ;";
System.out.println(sql);
conn.close();
conn=datasource.getConnection();
ps=conn.prepareStatement(sql1);
ps.executeUpdate();
conn.close();
conn=datasource.getConnection();
ps=conn.prepareStatement(sql);
int update = ps.executeUpdate();
sql = "select * from repayment_schedule  where id ="+id+";";
 
conn.close();
conn=datasource.getConnection();

ps=conn.prepareStatement(sql);
ResultSet rst = ps.executeQuery();

if(rst.next()) {
	Repayment_schedule rs1 = new Repayment_schedule();
rs1.setId(rst.getInt("id"));
rs1.setMonth_year(rst.getString("month_year"));
rs1.setLoan_id(rst.getInt("loan_id"));
rs1.setEMI(rst.getFloat("EMI"));
rs1.setPrincipal_component(rst.getFloat("principal_component"));
rs1.setInterest_component(rst.getFloat("interest_component"));
rs1.setPrincipal_outstanding(rst.getFloat("principal_outstanding"));
rs1.setPayment_status(rst.getString("payment_status"));
list.add(rs1);
}
}}

}
catch(Exception e)
{
throw new Exception(e);
}
return list;
}

@PostMapping("Home/Loan/Reshedule")// //shows current month reshedule
public List<Repayment_schedule> ViewrepaymentSchedule(@RequestBody Map<String,Object> temp ) throws Exception
{
	List<Repayment_schedule> list= new ArrayList<>();
System.out.println("sql");
//Repayment_schedule rs1 = new Repayment_schedule();
String username = (String) temp.get("username");
String sql = "select * from savings_accounts where email like '%"+username+"%'";
int bal =0;
System.out.println(sql);
try
{
Connection conn=datasource.getConnection();

PreparedStatement ps=conn.prepareStatement(sql);
ResultSet rs=ps.executeQuery();
int acc = 0;
int l = 0;
if(rs.next()) {
bal =  rs.getInt("balance");
 acc = rs.getInt("account_no");
}
sql = "select loan_id from loan_accounts where account_no = "+acc+"";
System.out.println(sql);
conn.close();
conn=datasource.getConnection();
ps=conn.prepareStatement(sql);
rs=ps.executeQuery();
if(rs.next()) {

 l = rs.getInt("loan_id");
System.out.println(l+" loan id");

}

sql = "select * from repayment_schedule where loan_id = "+l+" and payment_status ='pending'";
System.out.println(sql);
conn.close();
conn=datasource.getConnection();
ps=conn.prepareStatement(sql);
rs=ps.executeQuery();
int emi = 0;
int id = 0;
/*if(rs.next()) {
id = rs.getInt("id");
 emi = rs.getInt("emi");
System.out.println(emi+" lemi");

 sql = "select * from repayment_schedule  where id ="+id+";";*/
 
conn.close();
conn=datasource.getConnection();

ps=conn.prepareStatement(sql);
ResultSet rst = ps.executeQuery();

while(rst.next()) {
	Repayment_schedule rs1 = new Repayment_schedule();
rs1.setId(rst.getInt("id"));
rs1.setMonth_year(rst.getString("month_year"));
rs1.setLoan_id(rst.getInt("loan_id"));
rs1.setEMI(rst.getFloat("EMI"));
rs1.setPrincipal_component(rst.getFloat("principal_component"));
rs1.setInterest_component(rst.getFloat("interest_component"));
rs1.setPrincipal_outstanding(rst.getFloat("principal_outstanding"));
rs1.setPayment_status(rst.getString("payment_status"));
list.add(rs1);


}

}
catch(Exception e) {
throw new Exception(e);
}
return list;


}

@PostMapping("Home/Loan/ApplyLoan")
public Map<String,Object> applyloan(@RequestBody Map<String,Object> applyloan) throws Exception
{
String username= (String)applyloan.get("username");
String sql= "select * from savings_accounts where email like '"+username+"%'";
//ApplyLoanAccount a= new ApplyLoanAccount();
Map<String,Object> m = new HashMap<String,Object>();

try
{
Connection conn=datasource.getConnection();

PreparedStatement ps=conn.prepareStatement(sql);
ResultSet rs=ps.executeQuery();
int account_no = 0;
boolean exist = false;
int bal=0;
if(rs.next())
{
account_no= rs.getInt("account_no");
exist = rs.getBoolean("loan_exists");
bal = rs.getInt("balance");
}
if(exist) {

	System.out.println("loan_already exist");
   //a.setResult("Loan Already Exists");
   m.put("result","loan_already_exist");
   return a;
}
//int account_no = Integer.parseInt((String) applyloan.get("account_no"));

float salary= Float.parseFloat(""+( (String)applyloan.get("salary")));
int tenure= Integer.parseInt(""+(String)applyloan.get("tenure"));
float loan_amount= Float.parseFloat(""+(String)applyloan.get("loan_amount"));
String prop_address = (String)applyloan.get("property_address");
//String property_image=(String) applyloan.get("poperty_image");

System.out.println(salary);
System.out.println(tenure);
System.out.println(loan_amount);
System.out.println(prop_address);
System.out.println(username);


if(loan_amount<=50*salary)
{

	System.out.println("loan approved");
account_no= rs.getInt("account_no");
sql= "insert into loan_accounts(tenure,account_no,total_loan,interest_rate,property_address,loan_status) values('"+tenure+"','"+account_no+"','"+loan_amount+"','"+7.0+"','"+prop_address+"','Approved');";
System.out.println(sql);
PreparedStatement ps1 = conn.prepareStatement(sql);
ps1.execute();
conn.close ();
conn = null;
sql="update savings_accounts set loan_exists = true where account_no = "+account_no+";";
conn=datasource.getConnection();

ps=conn.prepareStatement(sql);
ps.execute();
//a.setResult("Loan Approved");
m.put("result","Loan Approved");

conn = null;
sql="update savings_accounts set balance ="+(bal+loan_amount)+"where account_no = "+account_no+";";
conn=datasource.getConnection();

ps=conn.prepareStatement(sql);
ps.execute();
conn = null;
sql="select loan_id from loan_accounts where account_no =  "+account_no+";";
conn=datasource.getConnection();

ps=conn.prepareStatement(sql);
rs=ps.executeQuery();
int loan_id = 0;

if(rs.next())
{
loan_id= rs.getInt("loan_id");

}
conn = null;


conn=datasource.getConnection();


sql ="call insert_repayment_from_loan("+loan_id+","+ loan_amount+","+ "7,"+tenure+" );";
PreparedStatement ps2 = conn.prepareStatement(sql);
ps2.execute();
conn.close();
conn=null;
System.out.println(sql);

}
else
{
//a.setResult("Loan Not Approved");
m.put("result","loan not Appoved");
}
}
catch(Exception e)
{
throw new Exception(e);
}
return m;
}

@PostMapping("Home/Loan/All")// //shows All schedule
public List<Users> ViewrepaymentScheduleAll(@RequestBody Map<String,Object> temp ) throws Exception
{
List<Users> ls = new ArrayList<Users>();

ls.add(new Users());
ls.add(new Users());
ls.add(new Users());
ls.add(new Users());

return ls;

}
@GetMapping("Home/Saving")//get loan table entries using account
public Savings_account  getSavings() throws Exception
{
//String username = (String) temp.get("username");
   String username="disha";
String sql = "select *from Savings_accounts where email like '%"+username+"%'";
Savings_account sa = new Savings_account();
System.out.println(sql);
try
{
Connection conn=datasource.getConnection();

PreparedStatement ps=conn.prepareStatement(sql);
ResultSet rs=ps.executeQuery();
String res="";
while(rs.next()) {
sa.setAccount_no(rs.getInt("account_no"));
sa.setName(rs.getString("name"));
sa.setBalance(rs.getFloat("balance"));
sa.setEmail(rs.getString("email"));
sa.setLoan_exists(rs.getBoolean("loan_exists") );
res+=" "+rs.getString(1);
        res+=" "+rs.getString(2);
        res+=" "+rs.getString(3);
        res+=" "+rs.getString(4);
        res+=" "+rs.getString(5);

       }
System.out.println(res);
conn.close();
    conn = null;
return sa;
     
}

catch(Exception e)
{
throw new Exception(e);
}
}




}
