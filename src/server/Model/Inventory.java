package server.Model;

import java.util.ArrayList;

public class Inventory {

	private ArrayList<Item> itemList;
	private Order dailyOrder;

	public Inventory(ArrayList<Item> itemList) {
		this.itemList = itemList;
		dailyOrder = new Order();
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

	public String updateItemQuantity(String itemName, int diff) {
		Item i = findItemByName(itemName);
		if (i != null)
			i.updateItemQuantity(diff);
		else 
			return itemName + " not found. Please verify tool name.";
		OrderLine ol = triggerOrderLine(i);
		if (ol != null) {
			i.updateItemQuantity(ol.getOrderQuantity());
			dailyOrder.addOrderLine(ol);
			return "Order line generated. Please click Print Order to see.";
		}
		return "The quantity of " + i.getItemName() + "has been updated.";
	}

	@SuppressWarnings("unused")
	public String addNewItem(int id, String type, String name, int quantity, double price, int supplierId) {
		Item i = null;
		i = new Item(id, type, name, quantity, price, supplierId);
		if (i != null) {
			itemList.add(i);
			return "New tool created.";
		}
		else
			return "Create new tool failed, please verify input and try again.";
	}

	public String deleteItem(String itemName) {
		Item i = findItemByName(itemName);
		if (i != null)
			itemList.remove(i);
		else 
			return itemName + " not found. Please verify tool name.";
		return i.getItemName() + "has been deleted.";
	}

	private OrderLine triggerOrderLine(Item theItem) {
		OrderLine ol = null;
		if (theItem.getItemQuantity() < 40) {
			ol = new OrderLine(theItem, 50 - theItem.getItemQuantity());
		}
		return ol;
	}
	
	public Order getOrder() {
		return dailyOrder;
	}
	
	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}
	
}
