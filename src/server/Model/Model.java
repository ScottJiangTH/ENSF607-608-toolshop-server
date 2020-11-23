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
	
	public Item findItemId(int itemId) {
		Item theItem = theInventory.findItemById(itemId);
		return theItem;
	}

	public Item findItemName(String itemName) {
		Item theItem = theInventory.findItemByName(itemName);
		return theItem;
	}

	public int checkItemQuantity(int itemId) {
		return theInventory.checkItemQuantity(itemId);
	}

	public int checkItemQuantity(String itemName) {
		return theInventory.checkItemQuantity(itemName);
	}

	public void updateItemQuantity(String itemName, int diff) {
		theInventory.updateItemQuantity(itemName, diff);

	}

	public void addNewItem(int id, String type, String name, int quantity, double price, int supplierId) {
		// TODO: add some return signal to GUI if supplier not found. e.g. prompt user
		// check input or create new supplier
		Supplier s = findSupplierById(supplierId);
		if (s != null) {
			theInventory.addNewItem(id, type, name, quantity, price, s);
		}
	}

	public void deleteItem(String itemName) {
		theInventory.deleteItem(itemName);
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

	public String printOrder() {
		return theInventory.printOrder();
	}

}
