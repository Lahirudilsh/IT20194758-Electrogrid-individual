package com;
import java.sql.*;

	
	

	public class Item {

		public Connection connect()
		{
		 Connection con = null;

		 try
		 {
			 Class.forName("com.mysql.jdbc.Driver");
			 con= DriverManager.getConnection("jdbc:mysql://localhost:3306/usermanagement", 
					 "root", "");
			 
			 
		 //For testing
		 System.out.print("Successfully connected");
		 }
		 
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }

		 return con;
		}
		
		public String readUsers()
		{ 
				String output = "";
				
				try
				{ 
						Connection con = connect();
						
						if (con == null) 
						{ 
							return "Error while connecting to the database for reading."; 
						} 
					 
			 // Prepare the html table to be displayed
					 output = "<table border='1'><tr><th>User Name</th>" 
								 +"<th>User NIC</th><th>Contact Number</th>"
								 + "<th>User Type</th>" 
								 + "<th>Update</th><th>Remove</th></tr>"; 
					 
					 String query = "select * from userdetails"; 
					 Statement stmt = con.createStatement(); 
					 ResultSet rs = stmt.executeQuery(query); 
					 
			 // iterate through the rows in the result set
					 while (rs.next()) 
					 { 
						 String userID = Integer.toString(rs.getInt("userID")); 
						 String userName = rs.getString("userName"); 
						 String userNIC = rs.getString("userNIC"); 
						 String contactNumber = Double.toString(rs.getDouble("contactNumber")); 
						 String userType = rs.getString("userType"); 
						 
			 // Add a row into the html table
						 output += "<tr><td><input id ='hidItemIDUpdate' name ='hidItemIDUpdate' type='hidden' value='" + userID + " '>"	+ userName + "</td>";
						
						 output += "<td>" + userNIC + "</td>"; 
						 output += "<td>" + contactNumber + "</td>"; 
						 output += "<td>" + userType + "</td>";
			 // buttons
						 output += "<td><input name='btnUpdate' id ='" + userID + " ' type='button' value='Update' class=' btnUpdate btn btn-secondary'></td><td>"
						 		+ "<input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-rechearcherid='"+ userID + " '>" + "</td></tr>";  
					 } 
					 con.close(); 
					 
			 // Complete the html table
					 output += "</table>"; 
					 
					 
					 
			 } 
				catch (Exception e) 
				{ 
					output = "Error while reading the items."; 
					System.err.println(e.getMessage()); 
				} 
				return output; 
		}

		public String insertUser(String username, String usernic,String contactnumber, String usertype)
	    {
				 String output = "";
				 
				 try
				 {
					 Connection con = connect();
					 
					 if (con == null)
					 {
						 return "Error while connecting to the database for inserting.";
					 }
					 
					 // create a prepared statement
					 String query = " insert into items(`userID`,`userName`,`userNIC`,`contactNumber`,`userType`)"+ " values (?, ?, ?, ?, ?)";
				 
				 
					 PreparedStatement preparedStmt = con.prepareStatement(query);
				 
					 // binding values
					 preparedStmt.setInt(1, 0);
					 preparedStmt.setString(2, username);
					 preparedStmt.setString(3, usernic);
					 preparedStmt.setDouble(4, Double.parseDouble(contactnumber));
					 preparedStmt.setString(5, usertype);
				 
				 
					 // execute the statement
					 preparedStmt.execute();
					 con.close();
					 
					 String newItems = readUsers();
					 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
				 }
				 catch (Exception e)
				 {
					 output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
					 System.err.println(e.getMessage());
					 
				 }
				 return output;
				 
				 
	    
	     
			
	    }
		public String updateUser(int userID,String userName,String userNIC,String contactNumber,String userType)
		{ 
				String output = ""; 
				try
				 { 
					 Connection con = connect(); 
					 if (con == null) 
					 { 
						 return "Error while connecting to the database for updating."; 
					 } 
				 // create a prepared statement
					 String query = "update items set  userName = ?,  userNIC = ?, contactNumber = ?, userType = ? where userID = ?"; 
					 
					 PreparedStatement preparedStmt = con.prepareStatement(query); 
					 
					 // binding values
					 preparedStmt.setString(1, userName);
					 preparedStmt.setString(2, userNIC);
					 preparedStmt.setDouble(3, Double.parseDouble(contactNumber));
					 preparedStmt.setString(4, userType);
					 preparedStmt.setInt(5, userID);


					 // execute the statement
					 preparedStmt.execute(); 
					 con.close(); 
					 
					 String newItems = readUsers();
					 output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}";
					
				 } 
				catch (Exception e) 
				 { 
					output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
					 System.err.println(e.getMessage()); 
				 } 
				return output; 
		}

		public String deleteUser(String userID)
		{ 
				String output = ""; 
				try
			    { 
					 Connection con = connect(); 
					 if (con == null) 
					 { 
						 return "Error while connecting to the database for deleting."; 
					 } 
				 // create a prepared statement
					 String query = "delete from userdetails where userID=?"; 
					 PreparedStatement preparedStmt = con.prepareStatement(query); 
					 // binding values
					 preparedStmt.setInt(1, Integer.parseInt(userID)); 

					 // execute the statement
					 preparedStmt.execute(); 
					 con.close(); 
					
					 String newUsers = readUsers();
					 output = "{\"status\":\"success\", \"data\": \"" +
			 newUsers + "\"}";
					 
				} 
				catch (Exception e) 
				{ 
					output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}"; 
					 System.err.println(e.getMessage()); 
			    } 
				return output; 
			}


		
	}
	


