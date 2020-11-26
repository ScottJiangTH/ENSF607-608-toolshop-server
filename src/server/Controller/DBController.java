package server.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import server.Model.*;

public class DBController {
	public Connection jdbc_connection;
	public Statement statement;
	public String localhost = "3306", databaseName = "toolshop";
	public String connectionInfo = "jdbc:mysql://localhost:" + localhost + "/" + databaseName+"?serverTimezone=UTC",  
			  login          = "root",
			  password       = "85984172Jls!";
	
	public DBController() {
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
			System.out.println("Connected to: " + connectionInfo + "\n");
		}
		catch(SQLException e) { e.printStackTrace(); }
		catch(Exception e) { e.printStackTrace(); }
	}
	
	public ResultSet readFromTable(String sql) {
		ResultSet rs = null;
		try
		{
			statement = jdbc_connection.prepareStatement(sql);
			rs = statement.executeQuery(sql);
		}
		catch(SQLException e) { e.printStackTrace(); }
		catch(Exception e) { e.printStackTrace(); }
		return rs;
	}
	
	public void updateTable(String sql) {
		try{
			statement = jdbc_connection.createStatement();
			statement.executeUpdate(sql);
		}
		catch(SQLException e)
		{e.printStackTrace();}
	}
	
	public ResultSet readWholeTable(String tableName) {
		// able to send result set to model controller to construct all types of objects
		String sql = "SELECT * FROM " + tableName;
		return readFromTable(sql);
	}
	
	public ResultSet searchFromTable(String tableName, String attributeName, String searchString) {
		// able to send result set to model controller to construct all types of objects
		String sql = "SELECT * FROM " + tableName + " WHERE " + attributeName + "=" + searchString;		
		return readFromTable(sql);
	}
	
	private void addItem(Item item) { 
		// never need to be called from other class since item is the super class
		// insert to item table on DB
		String sql = "INSERT INTO item " + " VALUES ( '"
				+ item.getItemId() + "', '" + 
				item.getItemType() + "', '" + 
				item.getItemName() + "', '" + 
				item.getItemDescription() + "', " + 
				item.getItemPrice() + ", " + 
				item.getItemQuantity() + ", '" + 
				item.getSupplierId() + "' );";
		updateTable(sql);
	}
	
	public void addElectricalItem(ElectricalItem eItem) {
		addItem(eItem);
		// insert to electrical item table on DB
		String sql = "INSERT INTO electrical_item " + " VALUES ( '"
				+ eItem.getItemId() + "', '" + 
				eItem.getPowerType() + "' );";
		updateTable(sql);
	}
	
	public void addNonElectricalItem(NonElectricalItem neItem) {
		// a wrapper method for non-electrical item to insert to table item on DB
		addItem(neItem);
	}
	
	private void addSupplier(Supplier supplier) {
		// insert to supplier table on DB
		String sql = "INSERT INTO supplier " + " VALUES ( '"
				+ supplier.getSupId() + "', '" + 
				supplier.getSupType() + "', '" + 
				supplier.getSupName() + "', '" + 
				supplier.getSupAddress() + "', '" + 
				supplier.getSupContactName() + "' );";
		updateTable(sql);
	}
	
	public void addInternationalSupplier(InternationalSupplier iSupplier) {
		addSupplier(iSupplier);
		// insert to international_supplier table on DB
		String sql = "INSERT INTO international_supplier " + " VALUES ( '"
				+ iSupplier.getSupId() + "', " + 
				iSupplier.getImportTax() + ");";
		updateTable(sql);
	}
	
	public void addPurchaseHistory(Customer customer, Item item, int purchaseQuantity) {
		// insert to purchase history table on DB
		String sql = "INSERT INTO purchase_history " + " VALUES ( '"
				+ customer.getCustomerId() + "', '" + 
				item.getItemId() + "', " + 
				purchaseQuantity + ");";
		updateTable(sql);
	}

	// need to check item quantity by itself
	public void updateItemQuantity(String itemName, int newQuantity) {
		String sql = "UPDATE item SET quantity = " + newQuantity + " WHERE iname = '" + itemName + "';";
		updateTable(sql);
	}

	public void addNewItem(int itemId, String itemType, String itemName, int itemQuantity, double itemPrice,
			int supplierId) {
		String sql = "INSERT INTO item " + " VALUES ( '"
				+ itemId + "', '" + 
				itemType + "', '" + 
				itemName + "', NULL, " + 
				itemQuantity + ", " + 
				itemPrice + ", '" + 
				supplierId + "' );";
		updateTable(sql);	
		
	}

	public void deleteItem(String itemName) {
		String sql = "DELETE FROM item WHERE iname = '" + itemName + "'";
		updateTable(sql);
	}

	public void addNewCustomer(int customerId, String firstName, String lastName, String address, String postalCode,
			String phone, String type) {
		String sql = "INSERT INTO customer " + " VALUES ( '"
				+ customerId + "', '" + 
				firstName + "', '" + 
				lastName + "', '" + 
				address + "', '" + 
				postalCode + "', '" + 
				phone + "', '" + 
				type + "' );";
		updateTable(sql);		
	}

	public void deleteCustomer(int customerId) {
		String sql = "DELETE FROM customer WHERE id = '" + customerId + "'";
		updateTable(sql);
	}

	public void updateCustomerInfo(int customerId, String firstName, String lastName, String address, String postalCode,
			String phone, String type) {
		String sql = "UPDATE item SET fname = '" + firstName
				+ "', lname = '" + lastName
				+ "', address = '" + address
				+ "', postal = '" + postalCode
				+ "', phone = '" + phone
				+ "', ctype = '" + type
				+ "', WHERE id = '" + customerId + "';";
		updateTable(sql);
		
	}
	
	public void saveDailyOrder(Order dailyOrder) {
		// insert to daily_order table on DB
		String sql = "INSERT INTO daily_order " + " VALUES ( '"
				+ dailyOrder.getOrderId() + "', '" + 
				dailyOrder.getOrderDate() + "' );";
		updateTable(sql);
		// insert to order_line table line by line
		ArrayList<OrderLine> olList = dailyOrder.getOrderLines();
		for (OrderLine ol : olList) {
			String olSql = "INSERT INTO order_line " + " VALUES ( '"
					+ dailyOrder.getOrderId() + "', '" + 
					ol.getTheItem().getItemId() + "', " + 
					ol.getOrderQuantity() + ", '" + 
					ol.getTheItem().getSupplierId() + "' );";
			updateTable(olSql);
		}
	}
}



