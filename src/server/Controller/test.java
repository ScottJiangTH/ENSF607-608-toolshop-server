package server.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import client.Controller.ClientController;

public class test {
	
	public Connection jdbc_connection;
	public Statement statement;
	public String localhost = "3306", databaseName = "toolshop";
	public String connectionInfo = "jdbc:mysql://localhost:" + localhost + "/" + databaseName+"?serverTimezone=UTC",  
			  login          = "root",
			  password       = "lindaA5585769.";
	
	public test() {
		try{
			// If this throws an error, make sure you have added the mySQL connector JAR to the project
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// If this fails make sure your connectionInfo and login/password are correct
			jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
			System.out.println("Connected to: " + connectionInfo + "\n");
		}
		catch(SQLException e) { e.printStackTrace(); }
		catch(Exception e) { e.printStackTrace(); }
	}
	private DefaultListModel clientList = new DefaultListModel();
	private static JList<ClientController> clientJList;
	private String tableName="customer";
	private int counter=0;
	
	
	
	public JList populateList() throws SQLException {
		String sql = "SELECT * FROM " + tableName;
		ResultSet clients;
		ClientController tempClient;
	//	try {
			PreparedStatement statement2 = jdbc_connection.prepareStatement(sql);
			clients = statement2.executeQuery();
			while(clients.next())
			{
				tempClient = new ClientController(clients.getInt("id"),
										clients.getString("fname"), 
										clients.getString("lname"),  
										clients.getString("address"),  
										clients.getString("postal"), 
										clients.getString("phone"), 
										clients.getString("ctype").charAt(0));
				clientList.add(counter,tempClient);
				counter = counter + 1;
			}
			clients.close();
			statement2.close();
			clientJList = new JList(clientList);
			return clientJList;
	//	} //catch (SQLException e) { e.printStackTrace(); }
		
		//return null;
	}
	
	
	public JList<ClientController > getClientJList() {
		return clientJList;
	}
	

	public DefaultListModel<ClientController> getClientList() {
		// TODO Auto-generated method stub
		return clientList;
	}
	
	
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		test test=new test();
		test.populateList();
		System.out.println(test.getClientList());
	}

}
