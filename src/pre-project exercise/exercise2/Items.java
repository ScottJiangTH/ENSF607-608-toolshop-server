package exercise2;



public class Items {
	private int itemQuantity;
	private int itemID;
	private String itemName;
	private double itemPrice;
	private int itemSupplier;
	private static final int MINIMUMNUMBER = 40;
	private static final int ORDERNUMBER = 50;
	private boolean alreadyOrdered;
	
	
	public Items (int id, String name, int quantity, double price, int supplierID) {
		itemID = id;
		itemName = name;
		itemQuantity = quantity;
		itemPrice = price;
		supplierID = itemSupplier;
		alreadyOrdered=false;	
	}
	
	

	public boolean decreaseItemQuantity() {
		if(itemQuantity>0) {
			itemQuantity--;
			return true;
		}else
			return false;
	}


	
	/**
	 * if item is already ordered, dont order another
	 * @return
	 */
	public boolean getAlreadyOrdered() {
		return alreadyOrdered;
	}
	
	/**
	 * get Item ID
	 * @return
	 */
	public int getItemID() {
		return itemID;
	}
	
	/**
	 * set Item ID
	 * @param itemID
	 */
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	/**
	 * get Item Name
	 * @return
	 */
	public String getItemName() {
		return itemName;
	}
	
	/**
	 * set Item Name
	 * @param name
	 */
	public void setItemName(String name) {
		itemName = name;
	}
	
	/**
	 * get item quantity
	 * @return
	 */
	public int getItemQuantity() {
		return itemQuantity;
	}
	
	/**
	 * set item quantity
	 * @param quantity
	 */
	public void setItemQuantity(int quantity) {
		itemQuantity = quantity;
	}
	
	/**
	 * get item price
	 * @return
	 */
	public double getItemPrice() {
		return itemPrice;
	}
	
	/**
	 * set item price
	 * @param price
	 */
	public void setItemPrice(int price) {
		itemPrice = price;
	}
	
	/**
	 * get supplier ID
	 * @return
	 */
	public int getItemSupplierID() {
		return itemSupplier;
	}
	
	/**
	 * set supplier ID
	 * @param supplierID
	 */
	public void setSupplierID(int supplierID) {
		setSupplierID(supplierID);
	}
	
	/**
	 * get the supplier ID
	 * @return
	 */
	public String toString() {
		return ("Item ID: " + itemID + ", Item Name: " + itemName + ", Item Price: $" + itemPrice + ", Current Item Quantity: " + itemQuantity + "\n");
	}
	

	

}
