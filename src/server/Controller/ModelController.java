package server.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

	// a signal sent from GUI that serves as signal that can be parsed and then call different functions
	private void listenSignal() throws IOException {
		while (true) { // loop run 1 time for every received singal
			// the signal we are looking for is in format: "optionSignal-1" if option 1 is selected from GUI
			String optionSignal = "";
			while (!optionSignal.contains("optionSignal")) {
				optionSignal = socketIn.readLine();
			}
			int option = Integer.parseInt(optionSignal);

			switch (option) {
			case 1:
				ArrayList<Item> allItems = model.listAllItems();
				socketOut.print(toJSON(allItems));
				// TODO: add error message return to GUI
				break;
			case 2:
				int itemId = Integer.parseInt(socketIn.readLine());
				Item i = model.findItemId(itemId);
				socketOut.print(toJSON(i));
				// TODO: add error message return to GUI
				break;
			case 3:
				String itemName = socketIn.readLine();
				i = model.findItemName(itemName);
				socketOut.print(toJSON(i));
				// TODO: add error message return to GUI
				break;
			case 4:
				itemId = Integer.parseInt(socketIn.readLine());
				int itemQuantity = model.checkItemQuantity(itemId);
				socketOut.print(itemQuantity);
				// TODO: add error message return to GUI
				break;
			case 5:
				itemName = socketIn.readLine();
				int itemQuantityByName = model.checkItemQuantity(itemName);
				socketOut.print(itemQuantityByName);
				// TODO: add error message return to GUI
				break;
			case 6:
				itemName = socketIn.readLine();
				int diff = Integer.parseInt(socketIn.readLine());
				model.updateItemQuantity(itemName, diff);
				// TODO: add error message return to GUI
				break;
			case 7:
				itemId = Integer.parseInt(socketIn.readLine());
				String itemType = socketIn.readLine();
				itemName = socketIn.readLine();
				itemQuantity = Integer.parseInt(socketIn.readLine());
				double itemPrice = Double.parseDouble(socketIn.readLine());
				int supplierId = Integer.parseInt(socketIn.readLine());
				model.addNewItem(itemId, itemType, itemName, itemQuantity, itemPrice, supplierId);
				break;
			case 8:
				itemName = socketIn.readLine();
				model.deleteItem(itemName);
				// TODO: add error message return to GUI
				break;
			case 9:
				supplierId = Integer.parseInt(socketIn.readLine());
				Supplier s = model.findSupplierById(supplierId);
				socketOut.print(toJSON(s));
				// TODO: add error message return to GUI
				break;
			case 10:
				String supplierName = socketIn.readLine();
				s = model.findSupplierByName(supplierName);
				socketOut.print(toJSON(s));
				// TODO: add error message return to GUI
				break;
			case 11:
				int customerId = Integer.parseInt(socketIn.readLine());
				Customer c = model.findCustomerById(customerId);
				socketOut.print(toJSON(c));
				break;
			case 12:
				String lastname = socketIn.readLine();
				ArrayList<Customer> cList= model.findCustomerbyLastName(lastname);
				socketOut.print(toJSON(cList));
				break;
			case 13:
				String cType = socketIn.readLine();
				cList = model.findCustomerbyType(cType);
				socketOut.print(toJSON(cList));
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
		// TODO write method to convert any object and ArrayList of object to JSON
		String s = "Just for Testing. To be replaced later. Printing object memory address below";
		s += o.toString();
		return s;
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
				String supplierID = rs.getString(7);
				Supplier theSupplier = matchSupplier(Integer.parseInt(supplierID));

				Item anItem = new Item(Integer.parseInt(ID), type, name, Integer.parseInt(quantity),
						Double.parseDouble(price), theSupplier);
				items.add(anItem);
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
		for (Supplier s : suppliers) {
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