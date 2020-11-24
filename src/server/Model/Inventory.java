package server.Model;

import java.util.ArrayList;

public class Inventory {

	private ArrayList<Item> itemList;
	private Order myOrder;

	public Inventory(ArrayList<Item> itemList) {
		this.itemList = itemList;
		myOrder = new Order();
	}

	public ArrayList<Item> listAllItems() {
		return itemList;
	}
	
	public Item findItemById(int itemId) {
		for (Item i : itemList) {
			if (i.getItemId() == itemId)
				return i;
		}
		return null;
	}

	public Item findItemByName(String itemName) {
		for (Item i : itemList) {
			if (i.getItemName().equals(itemName))
				return i;
		}
		return null;
	}

	public int checkItemQuantity(int itemId) {
		Item i = findItemById(itemId);
		int q = 0;
		if (i != null)
			q = i.getItemQuantity();
		return q;
	}

	public int checkItemQuantity(String itemName) {
		Item i = findItemByName(itemName);
		int q = 0;
		if (i != null)
			q = i.getItemQuantity();
		return q;
	}

	public void updateItemQuantity(String itemName, int diff) {
		// TODO: add some return signal to GUI if item not found
		Item i = findItemByName(itemName);
		if (i != null)
			i.updateItemQuantity(diff);
	}

	public void addNewItem(int id, String type, String name, int quantity, double price, int supplierId) {
		Item i = new Item(id, type, name, quantity, price, supplierId);
		itemList.add(i);
	}

	public void deleteItem(String itemName) {
		// TODO: add some return signal to GUI if item not found
		Item i = findItemByName(itemName);
		if (i != null)
			itemList.remove(i);
	}



	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}

	public void placeOrder(Item theItem) {
		OrderLine ol = theItem.generateOrderLine();
		if (ol != null) {
			myOrder.addOrderLine(ol);
		}
	}

	public String toString() {
		String str = "";
		for (Item i : itemList) {
			str += i;
		}
		return str;
	}

	public String printOrder() {
		return myOrder.toString();
	}
}
