/**
 * Exercise 1 for pre-project exercise
 *  @author Yunying Zhang, Tianhan Jiang
 */
package exercise;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
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
	public String databaseName = "InventoryDB", tableName = "ToolTable", dataFile = "items.txt";

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
	 * Create DataBase in SQL
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
	 * Create the ToolTable Table in the new SQL DataBase
	 */
	public void createTable() {
		String sql = "CREATE TABLE " + tableName + "(" + "ID INT(4) NOT NULL, " + "TOOLNAME VARCHAR(20) NOT NULL, "
				+ "QUANTITY INT(4) NOT NULL, " + "PRICE DOUBLE(5,2) NOT NULL, " + "SUPPLIERID INT(4) NOT NULL, "
				+ "PRIMARY KEY ( id ))";
		try {
			statement = jdbc_connection.createStatement();
			statement.executeUpdate(sql);
			System.out.println("Created Table " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to remove a table from the Data
	 */
	public void removeTable() {
		String sql = "DROP TABLE " + tableName;
		try {
			statement = jdbc_connection.createStatement();
			statement.executeUpdate(sql);
			System.out.println("Removed Table " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fill ToolTable Table with the list of tools by reading the text file
	 */
	public void fillTable() {
		try {
			Scanner sc = new Scanner(new FileReader(dataFile));
			while (sc.hasNext()) {
				String toolInfo[] = sc.nextLine().split(";");
				addItem(new Items(Integer.parseInt(toolInfo[0]), toolInfo[1], Integer.parseInt(toolInfo[2]),
						Double.parseDouble(toolInfo[3]), Integer.parseInt(toolInfo[4])));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("File " + dataFile + " Not Found!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a tool to the ToolTable Table
	 * 
	 * @param tool: the tool to be added
	 */
	public void addItem(Items tool) {
		String sql = "INSERT INTO " + tableName + " VALUES ( " + tool.getItemID() + ", '" + tool.getItemName() + "', "
				+ tool.getItemQuantity() + ", " + tool.getItemPrice() + ", " + tool.getItemSupplierID() + ");";
		try {
			statement = jdbc_connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Search for a tool within the SQL ToolTable Table, given its ID number
	 * 
	 * @param toolID: ID of the tool that the user wants to find
	 * @return
	 */
	public Items searchTool(int toolID) {
		String sql = "SELECT * FROM " + tableName + " WHERE ID=" + toolID;
		ResultSet tool;
		try {
			statement = jdbc_connection.createStatement();
			tool = statement.executeQuery(sql);
			if (tool.next()) {
				return new Items(tool.getInt("ID"), tool.getString("TOOLNAME"), tool.getInt("QUANTITY"),
						tool.getDouble("PRICE"), tool.getInt("SUPPLIERID"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * prints all of the tools in ToolTable
	 */
	public void printTable() {
		try {
			String sql = "SELECT * FROM " + tableName;
			statement = jdbc_connection.createStatement();
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