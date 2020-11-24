package server.Model;
/**
 * Stores information of each tool
 * @author Yunying Zhang
 * @Since October 10 2020
 *
 */
public class Item {
	
	private int itemId;
	private String itemType;
	private String itemName;
	private String itemDescription; 
	private double itemPrice;
	private int itemQuantity;
	private int supplierId;
	private boolean alreadyOrdered;
	private static final int ORDERQUANTITY = 40;
	private static final int MINIMUMUMBER = 20;
	
	
	
	public Item (int id, String type, String name, int quantity, double price, int supplierId) {
		
		this.itemId = id;
		this.itemType = type;
		this.itemName = name;
		this.itemQuantity = quantity;
		this.itemPrice = price;
		this.setSupplierId(supplierId); 
		setAlreadyOrdered(false);
	}

	public boolean decreaseItemQuantity () {
		if (itemQuantity > 0) {
			itemQuantity--;
		    return true;	
		}
		else
			return false;	
	}
	public OrderLine generateOrderLine (){
		OrderLine ol;
		if (getItemQuantity() < MINIMUMUMBER && alreadyOrdered == false){
			ol = new OrderLine (this, ORDERQUANTITY);
			alreadyOrdered = true;
			return ol;
		}
	    return null;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	public String toString () {
		return "Item ID: " + itemId + ", Item Name: " + itemName + ", Item Quantity: " + 
	           itemQuantity + "\n";
	}

	public boolean isAlreadyOrdered() {return alreadyOrdered;}

	public void setAlreadyOrdered(boolean alreadyOrdered) {
		this.alreadyOrdered = alreadyOrdered;
	}

	public String getItemType() {return itemType;}

	public void addItemDescription(String description) {
		this.itemDescription = description;
	}
	
	public String getItemDescription() {return itemDescription;}

	public void updateItemQuantity(int diff) {
		itemQuantity = itemQuantity + diff;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

}
