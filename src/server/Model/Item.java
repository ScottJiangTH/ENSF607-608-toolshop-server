package server.Model;
/**
 * Stores information of each tool
 * @author Yunying Zhang
 * @Since October 10 2020
 *
 */
public class Item {
	
	private int itemId;
<<<<<<< HEAD
	private int supplierId;
=======
	private String itemType;
>>>>>>> 621563286aab0135a9162b3c439a38b7adeb3b74
	private String itemName;
	private String itemDescription; 
	private double itemPrice;
	private int itemQuantity;
	private Supplier theSupplier;
	private boolean alreadyOrdered;
	private static final int ORDERQUANTITY = 40;
	private static final int MINIMUMUMBER = 20;
	
	
	
	public Item (int id, String type, String name, int quanitiy, double price, Supplier sup) {
		
		itemId = id;
		itemType = type;
		itemName = name;
		itemQuantity = quanitiy;
		itemPrice = price;
		sup = theSupplier; 
		setAlreadyOrdered(false);
	}
	
	public Item(int id, String name, int quanitiy, double price, int supId) {
		// TODO Auto-generated constructor stub
		itemId = id;
		itemName = name;
		itemQuantity = quanitiy;
		itemPrice = price;
		supplierId = supId; 
		
		
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
	public void setTheSupplier (Supplier sup) {
		theSupplier = sup;
	}
	public Supplier getTheSupplier () {
		return theSupplier;
	}
	
	public String toString () {
		return "Item ID: " + itemId + ", Item Name: " + itemName + ", Item Quantity: " + 
	           itemQuantity + "\n";
	}

	public boolean isAlreadyOrdered() {
		return alreadyOrdered;
	}

	public void setAlreadyOrdered(boolean alreadyOrdered) {
		this.alreadyOrdered = alreadyOrdered;
	}

<<<<<<< HEAD
	public int getSupplierID() {
		// TODO Auto-generated method stub
		return supplierId;
=======
	public String getItemType() {
		// TODO Auto-generated method stub
		return itemType;
	}

	public void addItemDescription(String description) {
		this.itemDescription = description;
	}
	
	public String getItemDescription() {
		// TODO Auto-generated method stub
		return itemDescription;
>>>>>>> 621563286aab0135a9162b3c439a38b7adeb3b74
	}

}
