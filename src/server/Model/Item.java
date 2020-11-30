package server.Model;

public abstract class Item {
	
	private int itemId;
	private String itemType;
	private String itemName;
	private String itemDescription; 
	private double itemPrice;
	private int itemQuantity;
	private int supplierId;
	
	public Item (int id, String type, String name, int quantity, double price, int supplierId) {
		
		this.itemId = id;
		this.setItemType(type);
		this.itemName = name;
		this.itemQuantity = quantity;
		this.itemPrice = price;
		this.setSupplierId(supplierId); 
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

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

}
