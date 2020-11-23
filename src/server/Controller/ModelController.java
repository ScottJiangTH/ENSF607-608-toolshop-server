package server.Controller;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.Model.*;

public class ModelController implements Runnable {

	// TODO: a signal sent from GUI that serves as signal that can be parsed and then call different functions
	// then the following inline functions becomes a switch structure
	private BufferedReader socketIn;
	private PrintWriter socketOut;	
	private DBController dBController;
	private Model model;
	private ArrayList <Supplier> suppliers;

	public ModelController(BufferedReader socketIn, PrintWriter socketOut) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}

	public Item findItemById(int itemId) {
		Item theItem = model.findItemId(itemId);
		return theItem;
	}
	
	public Item findItemByName(String itemName) {
		Item theItem = model.findItemName(itemName);
		return theItem;
	}
	
	public int checkItemQuantity(int itemId) {
		return model.checkItemQuantity(itemId);
	}
	
	public int checkItemQuantity(String itemName) {
		return model.checkItemQuantity(itemName);
	}
	
	public void updateItemQuantity(String itemName, int diff) {
		model.updateItemQuantity(itemName, diff);
	}
	
	public void addNewItem(int id, String type, String name, int quantity, double price, int supplierId) {
		model.addNewItem(id, type, name, quantity, price, supplierId);
	}
	
	public void deleteItem(String itemName) {
		model.deleteItem(itemName);
	}

	public Supplier findSupplierById(int supplierId) {
		return model.findSupplierById(supplierId);
	}
	
	public Supplier findSupplierByName(String supplierName) {
		return model.findSupplierByName(supplierName);
	}
	
	public Customer findCustomerById(int customerId) {
		return model.findCustomerById(customerId);
	}
	
	public ArrayList<Customer> findCustomerbyLastName(String lastname) {
		return model.findCustomerbyLastName(lastname);
	}
	
	public ArrayList<Customer> findCustomerbyType(String type) {
		return model.findCustomerbyType(type);
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
				suppliers.add(new Supplier(Integer.parseInt(supplierID), supplierType, companyName, address, salesContact));
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
	
	public ArrayList<Customer> allCustomers(){
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
				customers.add(new Customer(Integer.parseInt(customerID), firstname, lastname, address, postal, phone, type));
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
	}

}