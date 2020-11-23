package exercise;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import server.Model.Item;

// Pre-Project Exercise 

// This program allows you to create and manage a store inventory database.
// It creates a database and datatable, then populates that table with Items from
// items.txt.

public class InventoryManager {
	
	public Connection jdbc_connection;
	public Statement statement;
	public String databaseName = "InventoryDB", tableName = "ItemTable", dataFile = "items.txt";
	
	// Students should configure these variables for their own MySQL environment
	// If you have not created your first database in mySQL yet, you can leave the 
	// "[DATABASE NAME]" blank to get a connection and create one with the createDB() method.

	public String connectionInfo = "jdbc:mysql://localhost:3306/toolshop",  
				  login          = "root",
				  password       = "xxxx";

//	public String connectionInfo = "jdbc:mysql://localhost:3306/InventoryDB",  
//				  login          = "root",
//				  password       = "85984172Jls!";
//

	public InventoryManager()
	{
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
	
	// Use the jdbc connection to create a new database in MySQL. 
	// (e.g. if you are connected to "jdbc:mysql://localhost:3306", the database will be created at "jdbc:mysql://localhost:3306/InventoryDB")
	public void createDB()
	{
		try {
			statement = jdbc_connection.createStatement();
			statement.executeUpdate("CREATE DATABASE " + databaseName);
			System.out.println("Created Database " + databaseName);
		} 
		catch( SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Create a data table inside of the database to hold Items
	public void createTable()
	{
		String sql = "CREATE TABLE " + tableName + "(" +
				     "ID INT(4) NOT NULL, " +
				     "ItemNAME VARCHAR(20) NOT NULL, " + 
				     "QUANTITY INT(4) NOT NULL, " + 
				     "PRICE DOUBLE(5,2) NOT NULL, " + 
				     "SUPPLIERID INT(4) NOT NULL, " + 
				     "PRIMARY KEY ( id ))";
		try{
			statement = jdbc_connection.createStatement();
			statement.executeUpdate(sql);
			System.out.println("Created Table " + tableName);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	// Removes the data table from the database (and all the data held within it!)
	public void removeTable()
	{
		String sql = "DROP TABLE " + tableName;
		try{
			statement = jdbc_connection.createStatement();
			statement.executeUpdate(sql);
			System.out.println("Removed Table " + tableName);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	// Fills the data table with all the Items from the text file 'items.txt' if found
	public void fillTable()
	{
		try{
			Scanner sc = new Scanner(new FileReader(dataFile));
			while(sc.hasNext())
			{
				String ItemInfo[] = sc.nextLine().split(";");
				addItem( new Item( Integer.parseInt(ItemInfo[0]),
						                            ItemInfo[1],
						           Integer.parseInt(ItemInfo[2]),
						         Double.parseDouble(ItemInfo[3]),
						           Integer.parseInt(ItemInfo[4])) );
			}
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.err.println("File " + dataFile + " Not Found!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Add a Item to the database table
	public void addItem(Item Item)
	{
		String sql = "INSERT INTO " + tableName +
				" VALUES ( " + Item.getItemId() + ", '" + 
				Item.getItemName() + "', " + 
				Item.getItemQuantity() + ", " + 
				Item.getItemPrice() + ", " + 
				Item.getSupplierID() + ");";
		try{
			statement = jdbc_connection.createStatement();
			statement.executeUpdate(sql);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	// This method should search the database table for a Item matching the ItemID parameter and return that Item.
	// It should return null if no Items matching that ID are found.
	public Item searchItem(int ItemID)
	{
		String sql = "SELECT * FROM " + tableName + " WHERE ID=" + ItemID;
		ResultSet Item;
		try {
			statement = jdbc_connection.createStatement();
			Item = statement.executeQuery(sql);
			if(Item.next())
			{
				return new Item(Item.getInt("ID"),
								Item.getString("ItemNAME"), 
								Item.getInt("QUANTITY"), 
								Item.getDouble("PRICE"), 
								Item.getInt("SUPPLIERID"));
			}
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}

	// Prints all the items in the database to console
	public void printTable()
	{
		try {
			String sql = "SELECT * FROM " + tableName;
			statement = jdbc_connection.createStatement();
			ResultSet Items = statement.executeQuery(sql);
			System.out.println("Items:");
			while(Items.next())
			{
				System.out.println(Items.getInt("ID") + " " + 
								   Items.getString("ItemNAME") + " " + 
								   Items.getInt("QUANTITY") + " " + 
								   Items.getDouble("PRICE") + " " + 
								   Items.getInt("SUPPLIERID"));
			}
			Items.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		InventoryManager inventory = new InventoryManager();
		
		// You should comment this line out once the first database is created (either here or in MySQL workbench)
		//inventory.createDB();

		inventory.createTable();
		
		System.out.println("\nFilling the table with Items");
		inventory.fillTable();

		System.out.println("Reading all Items from the table:");
		inventory.printTable();

		System.out.println("\nSearching table for Item 1002: should return 'Grommets'");
		int ItemID = 1002;
		Item searchResult = inventory.searchItem(ItemID);
		if(searchResult == null)
			System.out.println("Search Failed to find a Item matching ID: " + ItemID);
		else
			System.out.println("Search Result: " + searchResult.toString());

		System.out.println("\nSearching table for Item 9000: should fail to find a Item");
		ItemID = 9000;
		searchResult = inventory.searchItem(ItemID);
		if(searchResult == null)
			System.out.println("Search Failed to find a Item matching ID: " + ItemID);
		else
			System.out.println("Search Result: " + searchResult.toString());
		
		System.out.println("\nTrying to remove the table");
		inventory.removeTable();
		
		try {
			inventory.statement.close();
			inventory.jdbc_connection.close();
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally
		{
			System.out.println("\nThe program is finished running");
		}
	}
}