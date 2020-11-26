package server.Model;

import java.util.ArrayList;

public class Model {

	private Inventory theInventory;
	private SupplierList theSupplierList;
	private CustomerList theCustomerList;

	public Model(Inventory theInventory, SupplierList theSupplierList, CustomerList theCustomerList) {
		this.theInventory = theInventory;
		this.theSupplierList = theSupplierList;
		this.theCustomerList = theCustomerList;
	}

	public ArrayList<Item> listAllItems(){
		ArrayList<Item> allItems = theInventory.listAllItems();
		return allItems;
	}
	
	public Item findItemById(int itemId) {
		return theInventory.findItemById(itemId);
	}

	public Item findItemByName(String itemName) {
		return theInventory.findItemByName(itemName);
	}

	public int checkItemQuantity(int itemId) {
		return theInventory.checkItemQuantity(itemId);
	}

	public int checkItemQuantity(String itemName) {
		return theInventory.checkItemQuantity(itemName);
	}

	public String updateItemQuantity(String itemName, int diff) {
		return theInventory.updateItemQuantity(itemName, diff);
	}

	public String addNewItem(int id, String type, String name, int quantity, double price, int supplierId) {
		Supplier s = findSupplierById(supplierId);
		String message;
		if (s != null) {
			message = theInventory.addNewItem(id, type, name, quantity, price, supplierId);
			s.getItemList().add(findItemById(id));
		}
		else 
			return "Supplier ID" + supplierId + "not found. Please verify supplier ID.";
		return message;
	}

	public String deleteItem(String itemName) {
		return theInventory.deleteItem(itemName);
	}

	public Supplier findSupplierById(int supplierId) {
		return theSupplierList.findSupplierById(supplierId);
	}

	public Supplier findSupplierByName(String supplierName) {
		return theSupplierList.findSupplierByName(supplierName);
	}

	public Customer findCustomerById(int customerId) {
		return theCustomerList.findCustomerById(customerId);
	}

	public ArrayList<Customer> findCustomerbyLastName(String lastname) {
		return theCustomerList.findCustomerById(lastname);
	}

	public ArrayList<Customer> findCustomerbyType(String type) {
		return theCustomerList.findCustomerByType(type);
	}

	public void addNewCustomer(int customerId, String firstName, String lastName, String address, String postalCode,
			String phone, String type) {
		theCustomerList.addNewCustomer(customerId, firstName, lastName, address, postalCode, phone, type);
	}

	public void deleteCustomer(int customerId) {
		theCustomerList.deleteCustomer(customerId);
	}

	public void updateCustomerInfo(int customerId, String firstName, String lastName, String address, String postalCode,
			String phone, String type) {
		theCustomerList.updateCustomerInfo(customerId, firstName, lastName, address, postalCode, phone, type);
	}
	
	public Order printOrder() {
		return theInventory.getOrder();
	}
}
