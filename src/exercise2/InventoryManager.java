/**
 * Exercise 2 for pre-project exercise
 * @author Yunying Zhang, Tianhan Jiang
 */
package exercise2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

// Pre-Project Exercise 

// This program allows you to create and manage a store inventory database.
// It creates a database and datatable, then populates that table with tools from
// items.txt.

public class InventoryManager {

	public Connection jdbc_connection;
	public Statement statement;
	public String databaseName = "InventoryDB2", tableName = "ToolTable", dataFile = "items.txt";

	// Students should configure these variables for their own MySQL environment
	// If you have not created your first database in mySQL yet, you can leave the
	// "[DATABASE NAME]" blank to get a connection and create one with the
	// createDB() method.
	public String connectionInfo = "jdbc:mysql://localhost:3306", login = "root", password = "xxxxxx";

	public InventoryManager() {
		try {
			// If this throws an error, make sure you have added the mySQL connector JAR to
			// the project

			// If this fails make sure your connectionInfo and login/password are correct
			jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
			System.out.println("Connected to: " + connectionInfo + "\n");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a new SQL DataBase
	 */
	public void createDB() {
		try {
			statement = jdbc_connection.createStatement();
			statement.executeUpdate("CREATE DATABASE " + databaseName);
			statement.executeUpdate("USE " + databaseName);
			System.out.println("Created Database " + databaseName);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a new dataTable inside of SQL
	 */
	public void createTable() {
		String sql = "CREATE TABLE " + tableName + "(" + "ID INT(4) NOT NULL, " + "TOOLNAME VARCHAR(20) NOT NULL, "
				+ "QUANTITY INT(4) NOT NULL, " + "PRICE DOUBLE(5,2) NOT NULL, " + "SUPPLIERID INT(4) NOT NULL, "
				+ "PRIMARY KEY ( id ))";
		try {
			// create prepareStatement
			statement = jdbc_connection.prepareStatement(sql);
			statement.executeUpdate(sql);
			System.out.println("Created Table " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * removes the tooltable
	 */
	public void removeTable() {
		String sql = "DROP TABLE " + tableName;
		try {
			statement = jdbc_connection.prepareStatement(sql);
			statement.executeUpdate(sql);
			System.out.println("Removed Table " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fills ToolTable with data from text file
	 */
	public void fillTable() {

		PreparedStatement insertData = null;
		String insertStatement = "INSERT INTO TOOLTABLE (ID,TOOLNAME,QUANTITY,PRICE,SUPPLIERID) VALUES(?,?,?,?,?);";

		try {
			Scanner sc = new Scanner(new FileReader(dataFile));
			while (sc.hasNext()) {
				String toolInfo[] = sc.nextLine().split(";");
				insertData = jdbc_connection.prepareStatement(insertStatement);
				insertData.setInt(1, Integer.parseInt(toolInfo[0]));
				insertData.setString(2, toolInfo[1]);
				insertData.setInt(3, Integer.parseInt(toolInfo[2]));
				insertData.setDouble(4, Double.parseDouble(toolInfo[3]));
				insertData.setInt(5, Integer.parseInt(toolInfo[4]));
				insertData.executeUpdate();

			}
			sc.close();
			insertData.close();
		} catch (FileNotFoundException e) {
			System.err.println("File " + dataFile + " Not Found!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add an tool to the ToolTable, tools are in format of ID, Name, Quantity,
	 * Price, supplierID
	 * 
	 * @param tool: tool be added to database
	 */
	public void addItem(Items tool) {
		PreparedStatement addTool = null;
		String sql = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?);";
		try {
			addTool = jdbc_connection.prepareStatement(sql);
			addTool.setInt(1, tool.getItemID());
			addTool.setString(2, tool.getItemName());
			addTool.setInt(3, tool.getItemQuantity());
			addTool.setDouble(4, tool.getItemPrice());
			addTool.setInt(5, tool.getItemSupplierID());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Search for a tool from the SQL DataBase given its IDNumber
	 * 
	 * @param toolID: ID of the tool that we want to search
	 * @return
	 */
	public Items searchTool(int toolID) {
		PreparedStatement getTool = null;
		String sql = "SELECT * FROM " + tableName + " WHERE ID = ? OR ID = ?";
		try {

			if (jdbc_connection != null) {
				getTool = jdbc_connection.prepareStatement(sql);
				getTool.setInt(1, toolID);
				getTool.setInt(2, toolID);
				ResultSet rs = getTool.executeQuery();
				if (rs.next()) {
					Items item = new Items(rs.getInt("ID"), rs.getString("TOOLNAME"), rs.getInt("QUANTITY"),
							rs.getDouble("PRICE"), rs.getInt("SUPPLIERID"));
					return item;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * print the whole table
	 */
	public void printTable() {
		try {
			String sql = "SELECT * FROM " + tableName;
			// create prepared statement
			statement = jdbc_connection.prepareStatement(sql);
			ResultSet tools = statement.executeQuery(sql);
			System.out.println("Tools:");
			while (tools.next()) {
				System.out.println(tools.getInt("ID") + " " + tools.getString("TOOLNAME") + " "
						+ tools.getInt("QUANTITY") + " " + tools.getDouble("PRICE") + " " + tools.getInt("SUPPLIERID"));
			}
			tools.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		InventoryManager inventory = new InventoryManager();

		// You should comment this line out once the first database is created (either
		// here or in MySQL workbench)
		inventory.createDB();

		inventory.createTable();

		System.out.println("\nFilling the table with tools");
		inventory.fillTable();

		System.out.println("Reading all tools from the table:");
		inventory.printTable();

		System.out.println("\nSearching table for tool 1002: should return 'Grommets'");
		int toolID = 1002;
		Items searchResult = inventory.searchTool(toolID);
		if (searchResult == null)
			System.out.println("Search Failed to find a tool matching ID: " + toolID);
		else
			System.out.println("Search Result: " + searchResult.toString());

		System.out.println("\nSearching table for tool 9000: should fail to find a tool");
		toolID = 9000;
		searchResult = inventory.searchTool(toolID);
		if (searchResult == null)
			System.out.println("Search Failed to find a tool matching ID: " + toolID);
		else
			System.out.println("Search Result: " + searchResult.toString());

		System.out.println("\nTrying to remove the table");
		inventory.removeTable();

		try {
			inventory.statement.close();
			inventory.jdbc_connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("\nThe program is finished running");
		}

	}

}