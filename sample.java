package project_jdbc;
import java.sql.*;
public class Sample {

	public static void main(String[] args) throws SQLException 
	{
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/demo","root","Kartiki@13");  
			//here sonoo is database name, root is username and password  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from table1");  
			while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2));  
			con.close();  
			}catch(Exception e)
		{ System.out.println(e);}  
			 
			  
	}
	
	}
		