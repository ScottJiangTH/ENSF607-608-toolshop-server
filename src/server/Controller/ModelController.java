package server.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import server.Model.*;

public class ModelController implements Runnable {
	
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private DBController dBController;
	private Model model;
	private ArrayList<Supplier> suppliers;

	public ModelController(BufferedReader socketIn, PrintWriter socketOut) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}

	// TODO: add methods to import from DB and construct specialized item, supplier, customer objects
	// TODO: add method to import from DB existing orders
	// TODO: add method to update DB at any time of the day
	// TODO: add method to update DB at the end the day, including daily_order
	// TODO: add method to print out daily_order at any time of the day
	
	// a signal sent from GUI that serves as signal that can be parsed and then call different functions
	private void listenSignal() throws IOException {
		while (true) { // loop run 1 time for every received singal
			// command is in format: "option",option#,arg1,arg2,...  DO NOT pass anything that is not in this format
			// e.g. for add new item: option,7,itemId,itemType,itemName,itemQuantity,itemPrice,supplierId
			String command = null;
			while (command == null) {
				command = socketIn.readLine();
			}
			
			String[] token = command.split(",");
			int option = Integer.parseInt(token[1]);
			
			switch (option) {
			case 1: // Print out all item
				ArrayList<Item> allItems = model.listAllItems();
				socketOut.println(toJSON(allItems));
				// TODO: add error message return to GUI
				break;
			case 2: // Find item by item ID
				int itemId = Integer.parseInt(token[2]);
				Item i = model.findItemById(itemId);
				socketOut.println(toJSON(i));
				// TODO: add error message return to GUI
				break;
			case 3: // Find item by item name
				i = model.findItemByName(token[2]);
				socketOut.println(toJSON(i));
				// TODO: add error message return to GUI
				break;
			case 4: // Check item quantity by item ID
				itemId = Integer.parseInt(token[2]);
				int itemQuantity = model.checkItemQuantity(itemId);
				socketOut.println(itemQuantity);
				// TODO: add error message return to GUI
				break;
			case 5: // Check item quantity by item name
				int itemQuantityByName = model.checkItemQuantity(token[2]);
				socketOut.println(itemQuantityByName);
				// TODO: add error message return to GUI
				break;
			case 6: // Update item quantity, can be either increment or decrement by any number
				int diff = Integer.parseInt(token[3]);
				model.updateItemQuantity(token[2], diff);
				// TODO: add error message return to GUI
				socketOut.println("The quantity of " + token[2] + " is updated.");
				break;
			case 7: // Add new item
				itemId = Integer.parseInt(token[2]);
				String itemType = token[3];
				String itemName = token[4];
				itemQuantity = Integer.parseInt(token[5]);
				double itemPrice = Double.parseDouble(token[6]);
				int supplierId = Integer.parseInt(token[7]);
				model.addNewItem(itemId, itemType, itemName, itemQuantity, itemPrice, supplierId);
				socketOut.println("New tool " + token[4] + " is added.");
				break;
			case 8: // Delete item
				model.deleteItem(token[2]);
				socketOut.println("Tool " + token[2] + " is deleted.");
				// TODO: add error message return to GUI
				break;
			case 9: // Find supplier by ID
				supplierId = Integer.parseInt(token[2]);
				Supplier s = model.findSupplierById(supplierId);
				socketOut.println(toJSON(s));
				// TODO: add error message return to GUI
				break;
			case 10: // Find supplier by name
				s = model.findSupplierByName(token[2]);
				socketOut.println(toJSON(s));
				// TODO: add error message return to GUI
				break;
			case 11: // Find customer by ID
				int customerId = Integer.parseInt(token[2]);
				Customer c = model.findCustomerById(customerId);
				socketOut.println(toJSON(c));
				break;
			case 12: // Find customer by last name, return a list
				ArrayList<Customer> cList= model.findCustomerbyLastName(token[2]);
				socketOut.println(toJSON(cList));
				break;
			case 13: // Find customer by type, return a list
				cList = model.findCustomerbyType(token[2]);
				socketOut.println(toJSON(cList));
				break;
			case 14:
				System.out.println("Client GUI closed.");
				return;
			default:
				System.out.println("Invalid selection. Please try again.\n");
				break;
			}
		}

	}

	private String toJSON(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	public ArrayList<Item> allItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		ResultSet rs = dBController.readWholeTable("item");

		try {
			while (rs.next()) {
				String ID = rs.getString(1);
				String type = rs.getString(2);
				String name = rs.getString(3);
				String quantity = rs.getString(6);
				String price = rs.getString(5);
				String supplierId = rs.getString(7);

				Item anItem = new Item(Integer.parseInt(ID), type, name, Integer.parseInt(quantity),
						Double.parseDouble(price), Integer.parseInt(supplierId));
				items.add(anItem);
				Supplier theSupplier = matchSupplier(Integer.parseInt(supplierId));
				theSupplier.getItemList().add(anItem);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}

	public ArrayList<Supplier> allSuppliers() {
		suppliers = new ArrayList<Supplier>();
		ResultSet rs = dBController.readWholeTable("supplier");

		try {
			while (rs.next()) {
				String supplierID = rs.getString(1);
				String supplierType = rs.getString(2);
				String companyName = rs.getString(3);
				String address = rs.getString(4);
				String salesContact = rs.getString(5);
				suppliers.add(
						new Supplier(Integer.parseInt(supplierID), supplierType, companyName, address, salesContact));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return suppliers;
	}

	private Supplier matchSupplier(int supplierId) {
		Supplier theSupplier = null;
		for (Supplier s : allSuppliers()) {
			if (s.getSupId() == supplierId) {
				theSupplier = s;
				break;
			}
		}
		return theSupplier;
	}

	public ArrayList<Customer> allCustomers() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		ResultSet rs = dBController.readWholeTable("customer");

		try {
			while (rs.next()) {
				String customerID = rs.getString(1);
				String firstname = rs.getString(2);
				String lastname = rs.getString(3);
				String address = rs.getString(4);
				String postal = rs.getString(5);
				String phone = rs.getString(6);
				String type = rs.getString(7);
				customers.add(
						new Customer(Integer.parseInt(customerID), firstname, lastname, address, postal, phone, type));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	@Override
	public void run() {
		this.dBController = new DBController();
		Inventory theInventory = new Inventory(allItems());
		SupplierList theSupplierList = new SupplierList(allSuppliers());
		CustomerList theCustomerList = new CustomerList(allCustomers());
		this.model = new Model(theInventory, theSupplierList, theCustomerList);
		try {
			listenSignal();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}