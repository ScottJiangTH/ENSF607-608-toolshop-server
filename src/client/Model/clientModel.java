package client.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import client.Controller.ClientController;

/**
 * This class serves as a JSON String parser, it contains methods that can
 * receive JSON type String and return various type of object including Item,
 * Supplier, Customer, and the ArrayList<> of these 3 types.
 * 
 * @author scottj
 *
 */
public class ClientModel {
	

	private DefaultListModel clientList = new DefaultListModel();
	private JList<ClientController> clientJList;
	private String tableName = "customer";
	private int counter = 0;

	public Connection jdbc_connection;
	public Statement statement;
	public String localhost = "3306", databaseName = "toolshop";
	public String connectionInfo = "jdbc:mysql://localhost:" + localhost + "/" + databaseName + "?serverTimezone=UTC",
			login = "root", password = "lindaA5585769.";

	public ClientModel() {
		try {
			// If this throws an error, make sure you have added the mySQL connector JAR to
			// the project
			Class.forName("com.mysql.cj.jdbc.Driver");

			// If this fails make sure your connectionInfo and login/password are correct
			jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
			System.out.println("Connected to: " + connectionInfo + "\n");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JList populateList() throws SQLException {
		String sql = "SELECT * FROM " + tableName;
		ResultSet clients;
		ClientController tempClient;
		// try {
		PreparedStatement statement2 = jdbc_connection.prepareStatement(sql);
		clients = statement2.executeQuery();
		while (clients.next()) {
			tempClient = new ClientController(clients.getInt("id"), clients.getString("fname"),
					clients.getString("lname"), clients.getString("address"), clients.getString("postal"),
					clients.getString("phone"), clients.getString("ctype").charAt(0));
			clientList.add(counter, tempClient);
			counter = counter + 1;
		}
		clients.close();
		statement2.close();
		clientJList = new JList(clientList);
		return clientJList;
		// } //catch (SQLException e) { e.printStackTrace(); }

		// return null;
	}

	public JList<ClientController> getClientJList() throws SQLException {
		// populateList();
		return clientJList;
	}

	public DefaultListModel<ClientController> getClientList() throws SQLException {
		// TODO Auto-generated method stub
		// populateList();
		return clientList;
	}

	public void deleteClient(ClientController check) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM "+ tableName + " WHERE  CLIENT_ID=?";
		try{
			PreparedStatement statement1 = jdbc_connection.prepareStatement(sql);
			statement1.setInt(1, check.getID());
			statement1.executeUpdate();
			statement1.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}

	public void addClient(ClientController client)
	{
		String sql = "INSERT INTO " + tableName +
				" VALUES ( ?,?,?,?,?,?,?)";
		try{
			PreparedStatement statement1 = jdbc_connection.prepareStatement(sql);
			
			statement1.setInt(1,client.getID());
			statement1.setString(2,client.getFirstName());
			statement1.setString(3,client.getLastName());
			statement1.setString(4,client.getAddress());
			statement1.setString(5,client.getPostalCode());
			statement1.setString(6,client.getPhoneNumber());
			statement1.setString(7, client.getType()+"");
//			clientList.add(counter,client);
//			counter = counter+1;
			
			statement1.executeUpdate();
			statement1.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	

}
