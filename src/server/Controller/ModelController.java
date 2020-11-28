package server.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

	// TODO: add methods to import from DB and construct specialized item, supplier,
	// customer objects
	// TODO: add method to import existing orders from DB, link to case 17

	private void communication() throws IOException {
		while (true) {
			// command is in format: "option",option#,arg1,arg2,... DO NOT pass anything
			// that is not in this format
			// e.g. for add new item:
			// option,7,itemId,itemType,itemName,itemQuantity,itemPrice,supplierId
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
				String message = model.updateItemQuantity(token[2], diff);
				int currentQuantity = model.checkItemQuantity(token[2]);
				dBController.updateItemQuantity(token[2], currentQuantity);
				socketOut.println(message);
				break;
			case 7: // Add new item
				itemId = Integer.parseInt(token[2]);
				String itemType = token[3];
				String itemName = token[4];
				itemQuantity = Integer.parseInt(token[5]);
				double itemPrice = Double.parseDouble(token[6]);
				int supplierId = Integer.parseInt(token[7]);
				if (token.length == 9) { // if there is power type entry
					if (itemType.equals("electrical")) {
						model.addNewEItem(itemId, itemType, itemName, itemQuantity, itemPrice, supplierId, token[8]);
						dBController.addElectricalItem(itemId, itemType, itemName, itemQuantity, itemPrice, supplierId,
								token[8]);
						socketOut.println("New electrical tool " + token[4] + " is added. Changes saved to database.");
					} else
						socketOut.println("Wrong tool type entry.");
				} else { // no power type entry
					if (itemType.equals("non-electrical")) {
						model.addNewNEItem(itemId, itemType, itemName, itemQuantity, itemPrice, supplierId);
						dBController.addNewItem(itemId, itemType, itemName, itemQuantity, itemPrice, supplierId);
						socketOut.println(
								"New non-electrical tool " + token[4] + " is added. Changes saved to database.");
					}
				}
				break;
			case 8: // Delete item by name
				message = model.deleteItem(token[2]);
				dBController.deleteItem(token[2]);
				socketOut.println(message);
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
				ArrayList<Customer> cList = model.findCustomerbyLastName(token[2]);
				socketOut.println(toJSON(cList));
				break;
			case 13: // Find customer by type, return a list
				cList = model.findCustomerbyType(token[2]);
				socketOut.println(toJSON(cList));
				break;
			case 14: // Add new customer
				customerId = Integer.parseInt(token[2]);
				String firstName = token[3];
				String lastName = token[4];
				String address = token[5];
				String postalCode = token[6];
				String phone = token[7];
				String type = token[8];
				model.addNewCustomer(customerId, firstName, lastName, address, postalCode, phone, type);
				dBController.addNewCustomer(customerId, firstName, lastName, address, postalCode, phone, type);
				socketOut.println(
						"New customer " + firstName + " " + lastName + " is added. Changes saved to database.");
				break;
			case 15: // Delete customer by id
				model.deleteCustomer(Integer.parseInt(token[2]));
				dBController.deleteCustomer(Integer.parseInt(token[2]));
				socketOut.println("Customer ID " + token[2] + " is deleted. Changes saved to database.");
				break;
			case 16: // updateCustomerInfo
				customerId = Integer.parseInt(token[2]);
				firstName = token[3];
				lastName = token[4];
				address = token[5];
				postalCode = token[6];
				phone = token[7];
				type = token[8];
				model.updateCustomerInfo(customerId, firstName, lastName, address, postalCode, phone, type);
				dBController.updateCustomerInfo(customerId, firstName, lastName, address, postalCode, phone, type);
				socketOut.println(firstName + lastName + "profile updated.");
				break;
			case 17: // print today's daily_order
				Order dailyOrder = model.getOrder();
				
				if (dailyOrder == null) { 
					socketOut.println("No order found.");
				} else {
					dBController.saveDailyOrder(dailyOrder);
					socketOut.println(toJSON(dailyOrder));
				}
				break;
			case 18: // print today's daily_order
				LocalDate date = LocalDate.parse(token[2]);
				Order historyOrder = findOrder(date); // searching DB for the order of selected day
				if (historyOrder == null) { 
					socketOut.println("No order found.");
				} else {
					socketOut.println(toJSON(historyOrder));
				}
				break;
			case 19:
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

	private Order findOrder(LocalDate date) {
		Order theOrder = null;
		ResultSet rsOrder = dBController.searchFromTable("daily_order", "odate", date.toString());
		try {
			if (rsOrder.next()) {
				String orderId = rsOrder.getString(1);
				LocalDate orderDate = rsOrder.getDate(2).toLocalDate();
				ArrayList<OrderLine> orderLines = new ArrayList<OrderLine>();
				ResultSet rsOrderline = dBController.searchFromTable("order_line", "oid", orderId);
				while (rsOrderline.next()) {
					String itemId = rsOrderline.getString(2);
					Item theItem = model.findItemById(Integer.parseInt(itemId));
					int orderQuantity = rsOrderline.getInt(3);
					OrderLine anOrderLine = new OrderLine(theItem, orderQuantity);
					orderLines.add(anOrderLine);
				}
				theOrder = new Order(Integer.parseInt(orderId), orderDate, orderLines);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return theOrder;
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
				Item anItem;
				String powerType = "";
				if (type.equals("electrical")) {
					ResultSet rsPower = dBController.searchFromTable("electrical_item", "iid", ID);
					if (rsPower.next())
						powerType = rsPower.getString(2);
					anItem = new ElectricalItem(Integer.parseInt(ID), type, name, Integer.parseInt(quantity),
							Double.parseDouble(price), Integer.parseInt(supplierId), powerType);
				} else
					anItem = new NonElectricalItem(Integer.parseInt(ID), type, name, Integer.parseInt(quantity),
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
				Supplier aSupplier;
				String importTax = "";
				if (supplierType.equals("international")) {
					ResultSet rsTax = dBController.searchFromTable("international_supplier", "sid", supplierID);
					if (rsTax.next())
						importTax = rsTax.getString(2);
					aSupplier = new InternationalSupplier(Integer.parseInt(supplierID), supplierType, companyName,
							address, salesContact, Float.parseFloat(importTax));
				} else
					aSupplier = new DomesticSupplier(Integer.parseInt(supplierID), supplierType, companyName, address,
							salesContact);
				suppliers.add(aSupplier);
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
				if (type.equals("R"))
					customers.add(new ResidentialCustomer(Integer.parseInt(customerID), firstname, lastname, address,
							postal, phone, type));
				else if (type.equals("C"))
					customers.add(new CommercialCustomer(Integer.parseInt(customerID), firstname, lastname, address,
							postal, phone, type));

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
		theInventory.setOrder(findOrder(LocalDate.now()));
		try {
			communication();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}